package com.whyr.bucketapi.service.utils;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ContentTypeUtils {

    private ContentTypeUtils() {

        throw new IllegalStateException("Utility class");
    }

    public static MediaType contentType(String filename) {

        String[] fileArrSplit = filename.split("\\.");
        String fileExtension = fileArrSplit[fileArrSplit.length - 1];
        switch (fileExtension) {
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
