package com.example.sac.SecuritiyThings.entities;

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
@ToString // TODO delete after implementation
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
    private Membership drafter; // for management purpose, id of the publisher(admin)

    @Column(nullable = false)
    private char important; // determines the post to be open to public or not ( y / n )

    @ColumnDefault("0")
    private int views; // for management purpose
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content; // TODO need to find out how can it be shown with visual effects on detail page

    @Column(nullable = true)
    private LocalDate effectiveDateB; // (when needed) this event is only applicable for 'Startdate' to Enddate
    @Column(nullable = true)
    private LocalDate effectiveDateE; // (when needed) this event is only applicable for Startdate to 'Enddate'
    @Column(nullable = true)
    private String attachment; // (when needed) attached file(name)

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime cTime; // to be used when load the list page with monthly sorted group
    @LastModifiedDate
    private LocalDateTime eTime; // // to be used when load the list page with monthly sorted group

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
                .attachment(this.getAttachment())
                .cTime(this.getCTime())
                .eTime(this.getETime())
                .build();
    }
}
