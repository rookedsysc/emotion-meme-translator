package com.meme.translator.emotion_meme_translator.service.meme;

import com.meme.translator.emotion_meme_translator.dto.MemeDto;
import com.meme.translator.emotion_meme_translator.dto.MemeRegisterDto;
import com.meme.translator.emotion_meme_translator.entity.Meme;
import com.meme.translator.emotion_meme_translator.repository.MemeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MemeServiceImpl implements MemeService {

    @Autowired
    private MemeRepository memeRepository;

    @Override
    public List<MemeDto> findMemeList() {
        List<MemeDto> memeDtoList = new ArrayList<>();
        List<Meme> memeList = memeRepository.findAll();
        for (Meme meme : memeList) {
            memeDtoList.add(new Meme().toMemeDto(meme));
        }
        return memeDtoList;
    }

    @Override
    public void saveMeme(MemeRegisterDto memeRegisterDto) {
        Meme memeEntity = Meme.builder()
                .title(memeRegisterDto.getTitle())
                .description(memeRegisterDto.getDescription())
                .originalImgName(memeRegisterDto.getOriginalImgName())
                .changedImgName(memeRegisterDto.getChangedImgName())
                .template(memeRegisterDto.getTemplate())
                .build();

        memeRepository.save(memeEntity);
    }

    @Override
    public String findTemplate(long memeId) {
        String template = memeRepository.findById(memeId)
                                        .orElseThrow(() -> new EntityNotFoundException("Meme not found with id: " + memeId))
                                        .getTemplate();
        return template;
    }

    @Override
    public MemeDto findMemeById(long memeId) {
        Meme meme = memeRepository.findById(memeId)
                                  .orElseThrow(() -> new EntityNotFoundException("Meme not found with id: " + memeId));
        MemeDto memeDto = new Meme().toMemeDto(meme);
        return memeDto;
    }

}
