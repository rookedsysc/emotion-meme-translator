package com.meme.translator.emotion_meme_translator.data;

import com.meme.translator.emotion_meme_translator.dto.EmotionMemeDto;
import java.util.List;

public class EmotionMemeData {

    public static final List<EmotionMemeDto> EMOTION_MEME_LIST = List.of(
            new EmotionMemeDto(0, "저쪽 집이 무너졌다고 해서 구경하러 갔죠.", "/images/sample1.jpeg", "저쪽 집이 무너졌다고 해서 구경하러 갔죠.\\n그런데 보고오니 우리 집이 무너진 거예요. 보자마자 눈물이 났어요."),
            new EmotionMemeDto(1, "안녕히 계세요 여러분 ~", "/images/sample2.jpeg", "가영이 안녕히 계세요 여러분~ 짤"),
            new EmotionMemeDto(2, "난 가끔 눈물을 흘린다.", "/images/sample3.png", "난… ㄱㅏ끔… 눈물을 흘린ㄷㅏ…")
    );
}
