package com.whyr.bucketapi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.whyr.bucketapi.service.exception.BadRequestException;
import com.whyr.bucketapi.service.exception.NotFoundException;
import com.whyr.bucketapi.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket-name}")
    private String awsBucketName;

    @Override
    public String uploadFile(MultipartFile file, String key) {

        log.info("Putting file to S3 Bucket: " + key);

        try {
            InputStream inputStream = file.getInputStream();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(inputStream.available());

            PutObjectResult putObjectResult = amazonS3.putObject(
                    new PutObjectRequest(awsBucketName, key, inputStream, objectMetadata));

            if (putObjectResult != null) {
                return key;
            }

        } catch (Exception e) {
            log.error("Error uploading file to S3: " + e, e);
            throw new BadRequestException("Error uploading file to S3");
        }

        return null;
    }

    @Override
    public ByteArrayOutputStream downloadFile(String key) {

        try {
            S3Object s3object = amazonS3.getObject(new GetObjectRequest(awsBucketName, key));

            InputStream is = s3object.getObjectContent();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }

            return outputStream;

        } catch (IOException ioException) {
            log.error("IO Exception: " + ioException, ioException);
            throw new ServiceException("IO Error");
        } catch (AmazonS3Exception amazonS3Exception) {
            log.error("Amazon S3 Exception: " + amazonS3Exception, amazonS3Exception);
            if (amazonS3Exception.getStatusCode() == 404) {
                throw new NotFoundException("File not found");
            } else {
                throw new BadRequestException("Error getting file from S3 Bucket");
            }
        }

    }

    @Override
    public Boolean deleteFile(String key) {

        amazonS3.deleteObject(new DeleteObjectRequest(awsBucketName, key));

        return true;
    }
}
