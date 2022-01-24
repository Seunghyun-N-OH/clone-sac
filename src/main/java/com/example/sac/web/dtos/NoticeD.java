package com.example.sac.web.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.sac.SecuritiyThings.entities.Membership;
import com.example.sac.SecuritiyThings.entities.Notice;

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
    private String attachment;
    private LocalDateTime cTime;
    private LocalDateTime eTime;

    public Notice toEntity() {
        return Notice.builder()
                .no(this.getNo())
                .category(this.getCategory())
                .drafter(Membership.builder().userId(this.drafter.getUserId()).build())
                .important(this.getImportant())
                .views(this.getViews())
                .title(this.getTitle())
                .content(this.getContent())
                .effectiveDateB(this.getEffectiveDateB())
                .effectiveDateE(this.getEffectiveDateE())
                .attachment(this.getAttachment())
                .build();
    }
}
