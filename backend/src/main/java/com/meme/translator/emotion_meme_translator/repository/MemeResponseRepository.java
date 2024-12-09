package com.meme.translator.emotion_meme_translator.repository;

import com.meme.translator.emotion_meme_translator.entity.MemeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemeResponseRepository extends JpaRepository<MemeHistory, Long> {
}
