package com.whyr.bucketapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

public interface FileService {

    String uploadFile(MultipartFile file, String key);

    ByteArrayOutputStream downloadFile(String key);

    Boolean deleteFile(String key);

}
