package de.manuelk2000.browsermicroservice.config;

import de.manuelk2000.browsermicroservice.service.storage.ObjectStorageCredentials;

public class StorageConfig {

    private ObjectStorageCredentials credentials;

    public StorageConfig() {

    }

    public StorageConfig(ObjectStorageCredentials credentials) {
        this.credentials = credentials;
    }

    public ObjectStorageCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(ObjectStorageCredentials credentials) {
        this.credentials = credentials;
    }
}
