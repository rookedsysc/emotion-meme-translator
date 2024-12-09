package com.meme.translator.emotion_meme_translator.service.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
public class GoogleCloudStorageService {

    @Value("${google.cloud.project-id}")
    private String projectId;

    @Getter
    @Value("${google.cloud.bucket-name}")
    private String bucketName;

    @Value("${google.cloud.key.filePath}")
    private String credentialsLocation;

    public String uploadFile(MultipartFile file) throws IOException {
        Resource resource = new ClassPathResource(credentialsLocation);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());

        Storage storage = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();

        String fileName = UUID.randomUUID() + "." + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        try (InputStream inputStream = file.getInputStream()) {
            storage.create(blobInfo, inputStream);
        }

        return fileName;
    }

    public Storage getStorageInstance() throws IOException {
        Resource resource = new ClassPathResource(credentialsLocation);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());

        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}

