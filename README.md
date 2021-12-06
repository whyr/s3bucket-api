# S3 Bucket API

## Summary

API to manage files stored in an Amazon S3 Bucket.

## Configuration

In order to use this API, edit the file *src/main/resources/application.properties* and set these properties related to the Amazon S3 Bucket:

```properties
aws.access-key-id=<TO_BE_SET>
aws.secret-access-key=<TO_BE_SET>
aws.s3.region=<TO_BE_SET>
aws.s3.bucket-name=<TO_BE_SET>
```

## Run the application

### Requirements

The application needs the following requirements:

- Maven

### Start the application

This application can be run using the Spring Boot plugin.

```shell
$ mvn spring-boot:run
```

If an IDE is used (i.e. IntelliJ IDEa), the application can be started running the method *main()* in *com.whyr.bucketapi.BucketApiApplication*

### REST API

Once the application is started, it exposes several endpoints documented in the built-in Swagger page:

```
http://localhost:8080/swagger-ui.html
```