package com.eventify.eventify.controller.teste;

import com.eventify.eventify.port.service.gcp.GcpStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class Teste {

    private GcpStorageService gcpStorageService;

    public Teste(GcpStorageService gcpStorageService) {
        this.gcpStorageService = gcpStorageService;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String imageUrl = gcpStorageService.uploadImage(file);
            return ResponseEntity.ok("Image uploaded successfully: " + imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading image: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<byte[]> downloadImage() {

        byte[] image;
        try {
            image = gcpStorageService.downloadImage("fileName.png");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(image);

    }

}
