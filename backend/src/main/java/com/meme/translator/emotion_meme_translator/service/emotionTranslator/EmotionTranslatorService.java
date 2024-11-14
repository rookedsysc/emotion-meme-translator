package com.meme.translator.emotion_meme_translator.service.emotionTranslator;

import com.meme.translator.emotion_meme_translator.dto.EmotionMemeDto;

import java.util.List;

public interface EmotionTranslatorService {
    List<EmotionMemeDto> findMemeList();
}
