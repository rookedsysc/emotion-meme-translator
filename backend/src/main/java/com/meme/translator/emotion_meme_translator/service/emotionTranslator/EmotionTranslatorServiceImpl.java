package com.meme.translator.emotion_meme_translator.service.emotionTranslator;

import com.meme.translator.emotion_meme_translator.data.EmotionMemeData;
import com.meme.translator.emotion_meme_translator.dto.EmotionMemeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmotionTranslatorServiceImpl implements EmotionTranslatorService {
    @Override
    public List<EmotionMemeDto> findMemeList() {
        // EmotionMemeData에서 데이터를 가져옵니다.
        return EmotionMemeData.EMOTION_MEME_LIST;
    }

}
