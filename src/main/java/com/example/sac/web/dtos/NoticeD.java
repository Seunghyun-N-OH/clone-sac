package com.example.sac.web.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.sac.SecuritiyThings.entities.Membership;
import com.example.sac.SecuritiyThings.entities.Notice;

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
    private MembershipD drafter;
    private char isPublic;
    private int views;
    private String title;
    private String content;
    private LocalDate effectiveDateB;
    private LocalDate effectiveDateE;
    private String attachment;
    private LocalDateTime cTime;
    private LocalDateTime eTime;

    public Notice toEntity() {
        return Notice.builder()
                .no(this.getNo())
                .drafter(Membership.builder().userId(this.drafter.getUserId()).build())
                .isPublic(this.getIsPublic())
                .views(this.getViews())
                .title(this.getTitle())
                .content(this.getContent())
                .effectiveDateB(this.getEffectiveDateB())
                .effectiveDateE(this.getEffectiveDateE())
                .attachment(this.getAttachment())
                .build();
    }
}
