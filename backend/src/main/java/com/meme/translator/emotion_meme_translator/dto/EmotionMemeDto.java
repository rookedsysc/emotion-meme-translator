package com.meme.translator.emotion_meme_translator.dto;

import lombok.Data;

@Data
public class EmotionMemeDto {

    private long id;
    private String title;
    private String image;
    private String description;
    private String prompt;

    // 생성자 수정: 각 필드를 초기화
    public EmotionMemeDto(long id, String title, String image, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        // prompt는 나중에 설정할 수 있도록 기본값 null
        this.prompt = null;
    }
}
