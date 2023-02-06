package com.hellog.domain.upload.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hellog.domain.upload.exception.FileUploadException;
import com.hellog.global.properties.S3Properties;
import lombok.RequiredArgsConstructor;
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
    private final S3Properties s3Properties;

    @Transactional(rollbackFor = Exception.class)
    public String uploadFile(MultipartFile multipartFile) {
        try {
            File convertFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("file convert error"));

            String fileName = s3Properties.getBucket() + "/" + UUID.randomUUID() + convertFile.getName();

            amazonS3Client.putObject(
                    new PutObjectRequest(s3Properties.getBucket(), fileName, convertFile).withCannedAcl(CannedAccessControlList.PublicRead));
            String uploadImageUrl = amazonS3Client.getUrl(s3Properties.getBucket(), fileName).toString();

            convertFile.delete();

            return uploadImageUrl;
        } catch (Exception e) {
            throw FileUploadException.EXCEPTION;
        }
    }

    public void delete(String path) {
        amazonS3Client.deleteObject(s3Properties.getBucket(), path);
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
