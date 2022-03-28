package de.manuelk2000.browsermicroservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = "app.properties";
    private final Properties properties;
    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    public Config() throws IOException {
        this.properties = new Properties();

        // Default properties
        properties.setProperty("host", "127.0.0.1");
        properties.setProperty("port", "7000");

        if(!Files.exists(Paths.get(CONFIG_FILE))){
            // Create config file if not exists
            store();
        }else{
            // Load properties from config file
            load();
        }

    }

    public void load() throws IOException {
        logger.debug("Load configuration file");
        properties.load(new FileInputStream(CONFIG_FILE));
    }

    public void store() throws IOException {
        logger.debug("Store configuration file");
        properties.store((new FileOutputStream(CONFIG_FILE)), "Browser Microservice Configuration");
    }

    public int getIntProperty(String key){
        return Integer.parseInt(properties.getProperty(key));
    }

    public String getStringProperty(String key){
        return properties.getProperty(key);
    }

}
