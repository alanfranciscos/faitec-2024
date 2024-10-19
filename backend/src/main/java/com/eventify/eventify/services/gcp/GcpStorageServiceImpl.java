package com.eventify.eventify.services.gcp;

import com.eventify.eventify.port.service.gcp.GcpStorageService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Service
public class GcpStorageServiceImpl implements GcpStorageService {
    private final Bucket bucket;

    @Value("${spring.cloud.gcp.bucket.image.base.path}")
    private String bucketImageStorage;

    public GcpStorageServiceImpl(@Value("${spring.cloud.gcp.storage.bucket}") String bucketName) {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        this.bucket = storage.get(bucketName);
    }

    private byte[] convertToPng(MultipartFile file) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "png", outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public String uploadImage(MultipartFile file, String fileName, String bucketPath) throws IOException {
//        List<String> fileNames = new ArrayList<>();
//
//        String userIdSearch = "user_1";
//        bucket.list(Storage.BlobListOption.prefix(userIdSearch)).iterateAll()
//                .forEach(blob -> fileNames.add(blob.getName()));
//
//        String fileName = file.getOriginalFilename()
        fileName = fileName.replaceAll("\\..+$", "") + ".png";
        byte[] imageData = convertToPng(file);

        bucketPath = bucketPath + fileName;
        Blob blob = bucket.create(bucketPath, imageData, "image/png");
        String link = this.bucketImageStorage +"/" + bucketPath;
        return link;
    }

    @Override
    public byte[] downloadImage(String fileName) throws IOException {
        Blob blob = bucket.get(fileName);
        if (blob != null && blob.exists()) {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                blob.downloadTo(outputStream);
                return outputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IOException("File not found in bucket");
    }
}
