package de.manuelk2000.browsermicroservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader {

    public static final File CONFIG_FILE = new File("config.yml");
    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private static ConfigLoader instance;
    private final ObjectMapper objectMapper;
    private Config config;

    private ConfigLoader() {
        // Configure yaml
        YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
        objectMapper = new ObjectMapper(yamlFactory);

        // Load configuration
        try {
            load();
        } catch (IOException e) {
            logger.error("Failed load configuration file!", e);
        }

        // Write configuration to file, if not exists
        if (!CONFIG_FILE.exists()) {
            try {
                save();
            } catch (IOException e) {
                logger.error("Failed create configuration file!", e);
            }
        }
    }

    public static ConfigLoader getInstance() {
        if (instance == null) {
            instance = new ConfigLoader();
        }
        return instance;
    }

    public void load() throws IOException {
        logger.info("Load configuration file.");

        // If config file not found use internal config from resources
        if (!CONFIG_FILE.exists()) {
            try {
                URL configURL = getClass().getClassLoader().getResource("config.yml");

                config = objectMapper.readValue(configURL, Config.class);
            } catch (IOException e) {
                logger.error("Failed to load default config file!", e);
            }
            return;
        }

        // Load config file
        try {
            String content = Files.readString(CONFIG_FILE.toPath());

            StringSubstitutor stringSubstituter = StringSubstitutor.createInterpolator();
            stringSubstituter.setEnableSubstitutionInVariables(true);
            content = stringSubstituter.replace(content);

            config = objectMapper.readValue(content, Config.class);
        } catch (IOException e) {
            logger.error("Failed to load config file!", e);
        }

        logger.debug("Config" + System.lineSeparator() + config);
    }

    public void save() throws IOException {
        logger.info("Write configuration file.");
        objectMapper.writeValue(CONFIG_FILE, config);
    }

    public Config getConfig() {
        if (this.config == null) {
            throw new IllegalStateException("Config file not loaded!");
        }
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
