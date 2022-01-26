package com.example.sac.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.sac.SecuritiyThings.entities.Membership;
import com.example.sac.web.dtos.MembershipD;
import com.example.sac.web.dtos.NoticeD;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
@Getter
@Table(name = "notice_board")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long no;

    @Column(nullable = false)
    private String category;
    // category [안내/회원/대관/아카데미/모집/발표]

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Membership drafter; // 관리목적, 작성자 이름은 회원의 id

    @Column(nullable = false)
    private char important; // 중요공지여부, y또는n

    @ColumnDefault("0")
    private int views; // 관리목적, 열람 수
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content; // 공지 내용, summernote사용

    @Column(nullable = true)
    private LocalDate effectiveDateB; // 이 공지가 언제부터 적용되는 공지인지
    @Column(nullable = true)
    private LocalDate effectiveDateE; // 이 공지가 언제까지 적용되는 공지인지

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime cTime; // 관리목적, 최초 작성된 시간, save()함수를 사용해 수정될 때 작성시간이 함께 바뀌지 않도록 updatable = false
    @LastModifiedDate
    private LocalDateTime eTime; // 관리목적, 마지막 수정시간

    // 공지사항 불러와 보여줄 때 dto타입으로 들고가기 위해 변환함수
    public NoticeD toDto() {
        return NoticeD.builder()
                .no(this.getNo())
                .category(this.getCategory())
                .drafter(MembershipD.builder().userId(this.drafter.getUserId()).build())
                .important(this.getImportant())
                .views(this.getViews())
                .title(this.getTitle())
                .content(this.getContent())
                .effectiveDateB(this.getEffectiveDateB())
                .effectiveDateE(this.getEffectiveDateE())
                .cTime(this.getCTime())
                .eTime(this.getETime())
                .build();
    }
}
