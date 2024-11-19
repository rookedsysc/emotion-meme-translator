package com.meme.translator.emotion_meme_translator.controller;

import com.meme.translator.emotion_meme_translator.data.EmotionMemeData;
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

        // EmotionMemeData에서 memeId와 일치하는 description을 찾기
        EmotionMemeDto selectedMeme = EmotionMemeData.EMOTION_MEME_LIST.stream()
                .filter(meme -> meme.getId() == memeId)
                .findFirst()
                .orElse(null);  // ID가 일치하는 것이 없으면 null 반환

        // 선택된 meme이 존재할 경우 description 가져오기
        String memeDescription = selectedMeme != null ? selectedMeme.getDescription() : "해당 ID에 맞는 밈을 찾을 수 없습니다.";

        // GPT에 전달할 프롬프트 준비
        String prompt = String.format("상황: '%s'에 맞춰 '%s' 문장을 자연스럽게 수정해줘.", situation, memeDescription);

        // GPT API를 통해 수정된 문장 받기
        String response = openAiService.getResponseFromGPT(prompt);

        return Map.of("response", response);
    }
}
