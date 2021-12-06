package com.triggle.bucketapi.controller;

import com.triggle.bucketapi.service.FileService;
import com.triggle.bucketapi.service.utils.ContentTypeUtils;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/file")
@Tag(name = "Bucket S3 Files")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @Parameter(name = "file", schema = @Schema(type = "string", format = "binary"), content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE), required = true) @RequestPart(name = "file") final MultipartFile file,
            @Parameter(name = "key", required = true) @RequestPart(name = "key") String key
    ) {

        return ResponseEntity.ok()
                .body(fileService.uploadFile(file, key));
    }

    @GetMapping("/")
    public ResponseEntity<byte[]> downloadFile(
            @Parameter(name = "key", required = true) @RequestParam(name = "key") String key
    ) {

        ByteArrayOutputStream downloadInputStream = fileService.downloadFile(key);

        String filename = StringUtils.getFilename(key);

        return ResponseEntity.ok()
                .contentType(ContentTypeUtils.contentType(filename))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(downloadInputStream.toByteArray());
    }

    @DeleteMapping(value = "/")
    public ResponseEntity<Boolean> uploadFile(
            @Parameter(name = "key", required = true) @RequestParam(name = "key") String key
    ) {

        return ResponseEntity.ok()
                .body(fileService.deleteFile(key));
    }

}
