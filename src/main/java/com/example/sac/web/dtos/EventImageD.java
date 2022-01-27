package com.example.sac.web.dtos;

import com.example.sac.domain.entities.EventImageE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EventImageD {
    private long imageId; // 이미지파일 id
    private String fileName; // 파일이름
    private String filePath; // 경로+이름

    public EventImageE toEntity() {
        return EventImageE.builder()
                .imageId(this.getImageId())
                .fileName(this.getFileName())
                .filePath(this.getFilePath())
                .build();
    }
}
