package com.eventify.eventify.port.service.gcp;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GcpStorageService {
    String uploadImage(MultipartFile file) throws IOException;

    byte[] downloadImage(String fileName) throws IOException;
}
