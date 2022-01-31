package com.example.sac.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
@Getter
@Setter
@ToString
public class EventDetailImg {
    @Id
    @GeneratedValue
    private long id; // 이미지파일 id

    @Column(nullable = false)
    private String fileName; // 파일이름

    @Column(nullable = false)
    private String filePath; // 경로(파일이름없음)
}
