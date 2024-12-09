package com.meme.translator.emotion_meme_translator.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeRegisterDto {

    private long id;
    private String title;
    private String description;
    private String template;
    private String originalImgName;
    private String changedImgName;
}
