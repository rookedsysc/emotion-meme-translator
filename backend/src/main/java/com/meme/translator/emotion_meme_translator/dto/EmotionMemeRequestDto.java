package com.meme.translator.emotion_meme_translator.dto;

import lombok.Data;

@Data
public class EmotionMemeRequestDto {
    private long id;        // 감정을 나타내는 ID
    private String prompt;  // 상황 설명
}
