package com.meme.translator.emotion_meme_translator.repository;

import com.meme.translator.emotion_meme_translator.entity.Meme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemeRepository extends JpaRepository<Meme, Long> {
}
