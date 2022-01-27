package com.example.sac.domain.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.sac.web.dtos.AttachedFileD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AttachedFileE {
    @Id
    private long fno; // 파일 하나당 생기는 id
    @Column(nullable = false)
    private String fileName; // 파일이름
    @Column(nullable = false)
    private String filePath; // 경로+파일이름
    @Column(nullable = false)
    private String fileContentType; // 파일의 content-type

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private NoticeE notice; // 등록된다면, 이게 어떤 공지의 첨부파일인지 알고있을 그 공지사항의 id

    // 불러올때 dto를 들고가기 위해 dto로 바꿔주는 함수
    public AttachedFileD toDto() {
        return AttachedFileD.builder()
                .fno(this.getFno())
                .fileName(this.getFileName())
                .filePath(this.getFilePath())
                .fileContentType(this.getFileContentType())
                .notice(this.getNotice().toDto())
                .build();
    }
}
