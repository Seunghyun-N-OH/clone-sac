package com.example.sac.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.sac.web.dtos.EventImageD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class EventImageE {
    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private long imageId; // 이미지파일 id

    @Column(nullable = false)
    private String fileName; // 파일이름

    @Column(nullable = false)
    private String filePath; // 경로+이름

    public EventImageD toDto() {
        return EventImageD.builder()
                .imageId(this.getImageId())
                .fileName(this.getFileName())
                .filePath(this.getFilePath())
                .build();
    }
}
