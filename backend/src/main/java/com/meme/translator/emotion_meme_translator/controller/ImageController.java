package com.meme.translator.emotion_meme_translator.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageController {

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            // resources/images 폴더에서 이미지 파일 로드
            Resource resource = new ClassPathResource("images/" + imageName);
            byte[] imageBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());

            // 이미지 타입에 따른 MediaType 설정
            String contentType = determineContentType(imageName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private String determineContentType(String imageName) {
        // 파일 확장자에 따른 Content-Type 반환
        if (imageName.endsWith(".png")) {
            return "image/png";
        } else if (imageName.endsWith(".jpg") || imageName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (imageName.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream";
    }
}
