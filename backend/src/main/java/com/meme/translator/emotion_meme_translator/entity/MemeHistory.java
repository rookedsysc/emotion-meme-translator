package com.meme.translator.emotion_meme_translator.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "meme_history")
@Getter
@Setter
public class MemeHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meme_id", nullable = false)
    private Meme meme;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String generatedText;

    @Column(nullable = false)
    private int likes = 0;
}
