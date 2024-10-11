package com.eventify.eventify.port.service.gcp;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GcpStorageService {
    String uploadImage(MultipartFile file, String fileName, String bucketPath) throws IOException;

    byte[] downloadImage(String fileName) throws IOException;
}
