package com.meme.translator.emotion_meme_translator.controller;

import com.meme.translator.emotion_meme_translator.data.EmotionMemeData;
import com.meme.translator.emotion_meme_translator.data.EmotionTemplateData;
import com.meme.translator.emotion_meme_translator.dto.EmotionMemeDto;
import com.meme.translator.emotion_meme_translator.dto.EmotionMemeRequestDto;
import com.meme.translator.emotion_meme_translator.service.emotionTranslator.EmotionTranslatorService;
import com.meme.translator.emotion_meme_translator.service.openAi.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/translator")
@RequiredArgsConstructor
public class EmotionTranslatorController {

    private final EmotionTranslatorService emotionTranslatorService;
    private final OpenAiService openAiService;

    // 1. 감정 리스트를 제공하는 엔드포인트
    @GetMapping("/emotions")
    public List<EmotionMemeDto> getMemeList() {
        List<EmotionMemeDto> memeList = emotionTranslatorService.findMemeList();
        return memeList;
    }

    // 2. 감정과 상황을 받아 GPT API에 요청하는 엔드포인트
    @PostMapping("/translate")
    public Map<String, String> translateEmotion(@RequestBody EmotionMemeRequestDto requestDto) {
        long memeId = requestDto.getId();  // 전달된 ID
        String situation = requestDto.getPrompt();  // 전달된 상황

        // GPT에 전달할 프롬프트 준비
        String prompt = EmotionTemplateData.formatTemplate(memeId) + situation;

        // GPT API를 통해 수정된 문장 받기
        String response = openAiService.getResponseFromGPT(prompt);

        return Map.of("response", response);
    }
}
