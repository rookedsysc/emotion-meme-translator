package com.meme.translator.emotion_meme_translator.controller;

import com.meme.translator.emotion_meme_translator.dto.MemeDto;
import com.meme.translator.emotion_meme_translator.dto.MemeRegisterDto;
import com.meme.translator.emotion_meme_translator.dto.MemeRequestDto;
import com.meme.translator.emotion_meme_translator.service.meme.MemeService;
import com.meme.translator.emotion_meme_translator.service.memeResponse.MemeResponseService;
import com.meme.translator.emotion_meme_translator.service.openAi.OpenAiService;
import com.meme.translator.emotion_meme_translator.service.storage.GoogleCloudStorageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meme")
@RequiredArgsConstructor
public class MemeController {

    private final MemeService memeService;
    private final MemeResponseService memeResponseService;
    private final OpenAiService openAiService;
    private final GoogleCloudStorageService storageService;

    // 1. 감정 리스트를 제공하는 엔드포인트
    @GetMapping("/emotions")
    public List<MemeDto> getMemeList() {
        List<MemeDto> memeList = memeService.findMemeList();
        return memeList;
    }

    // 2. 상세 페이지로
    @GetMapping("/detail/{memeId}")
    public ResponseEntity<MemeDto> getMemeDetail(@PathVariable("memeId") Long memeId) {
        try {
            MemeDto memeDto = memeService.findMemeById(memeId);
            return ResponseEntity.ok(memeDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. 감정과 상황을 받아 GPT API에 요청하는 엔드포인트
    @PostMapping("/translate")
    public Map<String, String> translateEmotion(@RequestBody MemeRequestDto requestDto) {
        long memeId = requestDto.getId();  // 전달된 ID
        String situation = requestDto.getPrompt();  // 전달된 상황

        // GPT에 전달할 프롬프트 준비
//        String prompt = EmotionTemplateData.formatTemplate(memeId) + situation;
        String prompt = memeService.findTemplate(memeId) + situation;

        // GPT API를 통해 수정된 문장 받기
        String response = openAiService.getResponseFromGPT(prompt);

        // 수정된 문장 저장하기
        memeResponseService.saveMemeResponse(memeId, response);

        return Map.of("response", response);
    }

    // 4. 밈을 등록 하는 API
    @Operation(
            summary = "Meme registration",
            description = "Register a meme image along with metadata"
    )
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerMeme(
            @RequestPart("image") MultipartFile image,  // 이미지 파일
            @RequestPart("title") String title,        // 밈 제목
            @RequestPart("description") String description,  // 밈 설명
            @RequestPart("template") String template      // GPT 프롬프트
    ) {
        try {
            // 파일 업로드 처리
            String uploadedFileName = storageService.uploadFile(image);

            // 데이터베이스에 정보 저장
            MemeRegisterDto memeRegisterDto = new MemeRegisterDto();
            memeRegisterDto.setTitle(title);
            memeRegisterDto.setDescription(description);
            memeRegisterDto.setTemplate(template);
            memeRegisterDto.setOriginalImgName(image.getOriginalFilename());
            memeRegisterDto.setChangedImgName(uploadedFileName);

            memeService.saveMeme(memeRegisterDto);

            // 로직 완료 응답
            return ResponseEntity.status(HttpStatus.CREATED).body("Meme registered successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register meme.");
        }
    }



}
