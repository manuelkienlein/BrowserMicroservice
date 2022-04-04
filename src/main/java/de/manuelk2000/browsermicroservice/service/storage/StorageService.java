package de.manuelk2000.browsermicroservice.service.storage;

import de.manuelk2000.browsermicroservice.config.StorageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);
    private final StorageConfig storageConfig;
    private final S3Driver storageDriver;

    public StorageService(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;

        if (storageConfig != null) {
            this.storageDriver = new AwsS3Driver(storageConfig.getCredentials());
        } else {
            this.storageDriver = null;
        }
    }

    public void put(String key, InputStream inputstream) {
        logger.info("Store object to " + key);
        if (this.storageDriver == null) {
            throw new UnsupportedOperationException("No storage driver is initialized!");
        }
        storageDriver
            .putObject(storageConfig.getCredentials().getBucket(), key, inputstream, "image/png");
    }

    public String sluggerizeUrl(String input) {
        Pattern NONLATIN = Pattern.compile("[^\\w-]");
        Pattern WHITESPACE = Pattern.compile("[\\s]");
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

}
