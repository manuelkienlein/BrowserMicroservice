package de.manuelk2000.browsermicroservice.service.storage;

import de.manuelk2000.browsermicroservice.config.StorageConfig;

import java.io.InputStream;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class StorageService {

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    private final StorageConfig storageConfig;
    private final S3Driver storageDriver;

    public StorageService(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
        this.storageDriver = new AwsS3Driver(storageConfig.getCredentials());
    }

    public void put(String key, InputStream inputstream) {

        storageDriver.putObject(storageConfig.getCredentials().getBucket(), key, inputstream, "image/png");

    }

    public String sluggerize(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

}
