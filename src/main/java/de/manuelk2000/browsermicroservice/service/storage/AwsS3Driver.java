package de.manuelk2000.browsermicroservice.service.storage;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.*;
import java.net.URI;
import java.util.List;

public class AwsS3Driver extends S3Driver {

    private final S3Client client;

    public AwsS3Driver(ObjectStorageCredentials credentials) {
        super(credentials);

        // S3 credentials
        AwsCredentials awsCredentials = AwsBasicCredentials.create(credentials.getAccessKey(), credentials.getSecretKey());

        // Build endpoint url
        String endpoint = (credentials.isUseSsl() ? "https" : "http") + "://" + credentials.getHost() + ":" + credentials.getPort();

        // Create client
        client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .endpointOverride(URI.create(endpoint))
                .region(Region.of(credentials.getRegion())) // test
                .build();
    }

    public void putObject(String bucket, String key, byte[] bytes, String contentType) {

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();

            PutObjectResponse response = client.putObject(request, RequestBody.fromBytes(bytes));
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
        } catch (SdkClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

    }

    public void putObject(String bucket, String key, InputStream inputStream, String contentType) {

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();

            byte[] objectBytes = new byte[0];
            try {
                objectBytes = inputStream.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            PutObjectResponse response = client.putObject(request, RequestBody.fromBytes(objectBytes));
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
        } catch (SdkClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

    }

    public void putObject(String bucket, String key, File file) {

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            PutObjectResponse response = client.putObject(request, RequestBody.fromFile(file));
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
        } catch (SdkClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

    }

    public byte[] getObjectBytes(String bucket, String key) {

        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            ResponseBytes<GetObjectResponse> response = client.getObjectAsBytes(request);

            return response.asByteArray();
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
        } catch (SdkClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

        return new byte[0];
    }

    public InputStream getObject(String bucket, String key) {

        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            ResponseBytes<GetObjectResponse> response = client.getObjectAsBytes(request);

            return response.asInputStream();
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
        } catch (SdkClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

        return InputStream.nullInputStream();
    }

    public void downloadObject(String bucket, String key, File file) {

        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            ResponseBytes<GetObjectResponse> response = client.getObjectAsBytes(request);

            // Write the data to a local file
            OutputStream os = new FileOutputStream(file);
            os.write(response.asByteArray());
            System.out.println("Successfully obtained bytes from an S3 object");
            os.close();

        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
        } catch (SdkClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<S3Object> getObjects(String bucket) {

        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucket)
                    .build();

            ListObjectsResponse response = client.listObjects(listObjects);
            List<S3Object> objects = response.contents();

            return objects;
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return null;
    }

    public void deleteObject(String bucket, String key) {

        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            client.deleteObject(deleteObjectRequest);
        } catch (AwsServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
        } catch (SdkClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

    }

}
