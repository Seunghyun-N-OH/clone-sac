package com.example.sac.web.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.sac.SecuritiyThings.entities.Membership;
import com.example.sac.domain.entities.NoticeE;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeD {
    private long no;
    private String category;
    private MembershipD drafter;
    private char important;
    private int views;
    private String title;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDateB;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDateE;
    private LocalDateTime cTime;
    private LocalDateTime eTime;

    public NoticeE toEntity() {
        return NoticeE.builder()
                .no(this.getNo())
                .category(this.getCategory())
                .drafter(Membership.builder().userId(this.drafter.getUserId()).build())
                .important(this.getImportant())
                .views(this.getViews())
                .title(this.getTitle())
                .content(this.getContent())
                .effectiveDateB(this.getEffectiveDateB())
                .effectiveDateE(this.getEffectiveDateE())
                .build();
    }

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
