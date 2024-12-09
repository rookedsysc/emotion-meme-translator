package com.meme.translator.emotion_meme_translator.service.meme;

import com.meme.translator.emotion_meme_translator.dto.MemeDto;
import com.meme.translator.emotion_meme_translator.dto.MemeRegisterDto;

import java.util.List;

public interface MemeService {
    List<MemeDto> findMemeList();

    void saveMeme(MemeRegisterDto memeRegisterDto);

    String findTemplate(long memeId);

    MemeDto findMemeById(long memeId);
}
