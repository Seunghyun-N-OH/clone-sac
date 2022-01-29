package com.example.sac.web.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.sac.domain.entities.AttachedFile;

import org.springframework.format.annotation.DateTimeFormat;

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
public class NoticeD {
    private long no;
    private String category;
    private String drafter;
    private char important;
    private int views;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDateB;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDateE;
    private List<AttachedFile> attachment;
    private LocalDateTime cTime;
    private LocalDateTime eTime;

    public NoticeLD toListDto() {
        NoticeLD a = new NoticeLD();
        a.setNo(this.getNo());
        a.setCategory(this.getCategory());
        a.setTitle(this.getTitle());
        a.setEffectiveDateB(this.getEffectiveDateB());
        a.setEffectiveDateE(this.getEffectiveDateE());
        return a;
    }
}
