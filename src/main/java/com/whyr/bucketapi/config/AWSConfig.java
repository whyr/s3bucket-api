package com.whyr.bucketapi.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Value("${aws.access-key-id}")
    private String awsAccesKeyId;

    @Value("${aws.secret-access-key}")
    private String awsSecretAccessKey;

    @Value("${aws.s3.region}")
    private String awsS3Region;

    @Bean
    public AmazonS3 amazonS3Client() {

        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(awsAccesKeyId, awsSecretAccessKey);

        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(awsS3Region))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();

    }

}
