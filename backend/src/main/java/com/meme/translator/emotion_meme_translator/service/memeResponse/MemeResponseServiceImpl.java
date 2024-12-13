package com.meme.translator.emotion_meme_translator.service.memeResponse;

import com.meme.translator.emotion_meme_translator.entity.Meme;
import com.meme.translator.emotion_meme_translator.entity.MemeHistory;
import com.meme.translator.emotion_meme_translator.repository.MemeRepository;
import com.meme.translator.emotion_meme_translator.repository.MemeResponseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemeResponseServiceImpl implements MemeResponseService {

    @Autowired
    private MemeResponseRepository memeResponseRepository;

    @Autowired
    private MemeRepository memeRepository;

    @Override
    public void saveMemeResponse(long memeId, String response) {
        Meme meme = memeRepository.findById(memeId)
                                  .orElseThrow(() -> new EntityNotFoundException("Meme not found with id: " + memeId));
        MemeHistory memeHistory = new MemeHistory();
        memeHistory.setMeme(meme);
        memeHistory.setGeneratedText(response);
        memeResponseRepository.save(memeHistory);
    }
}
