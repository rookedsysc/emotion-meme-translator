package com.meme.translator.emotion_meme_translator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemeDto {

    private long id;
    private String title;
    private String description;
    private String changedImgName;
}
