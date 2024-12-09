package com.meme.translator.emotion_meme_translator.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.meme.translator.emotion_meme_translator.service.storage.GoogleCloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;

//    @GetMapping("/images/{imageName}")
//    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
//        try {
//            // resources/images 폴더에서 이미지 파일 로드
//            Resource resource = new ClassPathResource("images/" + imageName);
//            byte[] imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
//
//            // 이미지 타입에 따른 MediaType 설정
//            String contentType = determineContentType(imageName);
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(contentType))
//                    .body(imageBytes);
//        } catch (IOException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            // Google Cloud Storage에서 이미지 데이터 가져오기
            Storage storage = googleCloudStorageService.getStorageInstance();
            Blob blob = storage.get(BlobId.of(googleCloudStorageService.getBucketName(), imageName));

            if (blob == null || !blob.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 바이너리 데이터 가져오기
            byte[] imageBytes = blob.getContent();

            // 파일 확장자에 따라 Content-Type 설정
            String contentType = determineContentType(imageName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String determineContentType(String imageName) {
        // 파일 확장자에 따른 Content-Type 반환
        if (imageName.endsWith(".png")) {
            return "image/png";
        } else if (imageName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (imageName.endsWith(".jpg")) {
            return "image/jpg";
        } else if (imageName.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream";
    }
}
