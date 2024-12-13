package com.meme.translator.emotion_meme_translator.entity;

import com.meme.translator.emotion_meme_translator.dto.MemeDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meme")
@Getter
@Setter
@NoArgsConstructor  // JPA에서 사용하기 위한 기본 생성자
@AllArgsConstructor // @Builder와 함께 사용하기 위한 모든 필드 생성자
@Builder            // Builder 패턴 추가
public class Meme extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String template;

    @Column(nullable = false)
    private String originalImgName;

    @Column(nullable = false)
    private String changedImgName;

    public MemeDto toMemeDto(Meme meme) {
        MemeDto memeDto = new MemeDto();
        memeDto.setId(meme.getId());
        memeDto.setTitle(meme.getTitle());
        memeDto.setDescription(meme.getDescription());
        memeDto.setChangedImgName(meme.getChangedImgName());

        return memeDto;
    }
}
