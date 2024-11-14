package com.meme.translator.emotion_meme_translator.controller;

import com.meme.translator.emotion_meme_translator.dto.EmotionMemeDto;
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

}
