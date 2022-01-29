package com.example.sac.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class AttachedFile {
    @Id
    @GeneratedValue
    private long fno; // 파일 하나당 생기는 id
    @Column(nullable = false)
    private String fileName; // 파일이름
    @Column(nullable = false)
    private String filePath; // 경로+파일이름
    @Column(nullable = false)
    private String fileContentType; // 파일의 content-type

    @ManyToOne
    private NoticeE notice; // 등록된다면, 이게 어떤 공지의 첨부파일인지 알고있을 그 공지사항의 id

    void setNotice(NoticeE n) {
        this.notice = n;
    }
}
