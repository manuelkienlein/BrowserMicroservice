package net.ulinky.browsermicroservice.service.storage;

import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public abstract class S3Driver {

    public S3Driver(ObjectStorageCredentials credentials) {

    }

    public abstract void putObject(String bucket, String key, byte[] bytes, String contentType);

    public abstract void putObject(String bucket, String key, InputStream inputStream,
        String contentType);

    public abstract void putObject(String bucket, String key, File file);

    public abstract byte[] getObjectBytes(String bucket, String key);

    public abstract InputStream getObject(String bucket, String key);

    public abstract void downloadObject(String bucket, String key, File file);

    public abstract List<S3Object> getObjects(String bucket);

    public abstract void deleteObject(String bucket, String key);

}
