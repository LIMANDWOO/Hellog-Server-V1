package com.hellog.domain.upload.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hellog.domain.upload.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional(rollbackFor = Exception.class)
    public String uploadFile(MultipartFile multipartFile) {
        try {
            File convertFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("file convert error"));

            String fileName = bucket + "/" + UUID.randomUUID() + convertFile.getName();

            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, fileName, convertFile).withCannedAcl(CannedAccessControlList.PublicRead));
            String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();

            convertFile.delete();

            return uploadImageUrl;
        } catch (Exception e) {
            throw FileUploadException.EXCEPTION;
        }
    }

    public void delete(String path) {
        amazonS3Client.deleteObject(bucket, path);
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
