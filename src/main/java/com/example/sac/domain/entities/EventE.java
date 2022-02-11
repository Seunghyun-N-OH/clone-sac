package com.example.sac.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.example.sac.domain.services.functions.UpAndDownFile;
import com.example.sac.web.dtos.EventD;
import com.example.sac.web.dtos.EventLD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class EventE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // [1, 2, 3, 4 ...]
    // 행사마다 부여되는 id
    @Column(nullable = false)
    private String eventGroup; // [exhibition, show]
    // 전시인지 공연인지 구분
    @Column(nullable = false)
    private String venue1; // [음악당, 오페라하우스, 전시장]
    // 진행장소 대분류
    @Column(nullable = false)
    private String venue2; // [한가람디자인미술관, 한가람미술관]
    // 진행장소 중분류
    @Column(nullable = true)
    private String venue3; // [1층, 2층, 특별관]
    // 진행장소 소분류
    @Column(nullable = false)
    private char sacPlanned; // [c, y, n]
    // sac 기획행사인지여부
    @Column(nullable = true)
    private String eventTitle; // [라이프사진전, 샤갈달리뷔페]
    // 행사 명
    @ElementCollection
    @Column(nullable = true)
    private List<String> host; // [동아일보]
    // 주관
    @ElementCollection
    @CollectionTable(name = "organizer")
    @Builder.Default
    @Column(nullable = true)
    private List<String> organizer = new ArrayList<>(); // [영국문화원]
    // 주최
    @ElementCollection
    @CollectionTable(name = "sponsor")
    @Builder.Default
    @Column(nullable = true)
    private List<String> sponsor = new ArrayList<>(); // [외교부]
    // 후원/협찬
    @Column(nullable = true)
    private String requiredAge; // [7, 18 ...]
    // 입장/관람 가능 나이제한
    @Column(nullable = false)
    private char onSale; // [s, o, p, c, i]
    // 입장권 판매상태 (s)ite, (o)nline, (p)lanned, (c)losed, (i)nvitational

    @OneToMany(mappedBy = "event")
    @Builder.Default // 1:N
    private List<PricingPolicy> pricingPolicy = new ArrayList<>(); // [성인-15000, 청소년-12000 ...]
    // 가격정책

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "poster_id") // 단방향 1:1 [포스터는 1개만 등록받을 것]
    private EventPoster poster; // TODO 파일 받을 때 이미지파일 맞는지 타입체크기능 추가 필요
    // 포스터

    @OneToMany // 단방향 1:N [상세페이지는 여러개가 될 수 있음]
    @JoinColumn(name = "event_id")
    @Builder.Default
    private List<EventDetailImg> detail_img = new ArrayList<>(); // TODO 파일 받을 때 이미지파일 맞는지 타입체크기능 추가 필요
    // 상세페이지

    @Column(nullable = false)
    private String contact; // [010-5516-2461]
    // 문의전화

    // [일정] 행사가 기간으로 진행되는 경우 입력 ##################################################
    @Column(nullable = true)
    private LocalDate openDate; // [2022-01-27]
    // 행사 개막일
    @Column(nullable = true)
    private LocalDate finDate; // [2022-01-29]
    // 행사 폐막일
    @Column(nullable = true)
    private String openTime; // [12:00]
    // 일별 행사 오픈시간
    @Column(nullable = true)
    private String lastEntrance; // [17:00]
    // 일별 행사 마감시간
    @Column(nullable = true)
    private String closeTime; // [18:00]
    // 일별 행사 종료시간

    // [일정] 행사가 회차별로 진행되는 경우 입력 #################################################
    @ElementCollection
    @CollectionTable(name = "eventTime")
    private List<LocalDateTime> eventTime; // [2022-01-27 12:00]
    // 회차별 시작시간
    @Column(nullable = true)
    private int runningTime; // [90, 100, 180 ...]
    // 러닝타임

    @Column(nullable = true)
    private String eventNews;

    public EventD toDto() {
        return EventD.builder()
                .id(this.id)
                .eventGroup(this.eventGroup)
                .venue1(this.venue1)
                .venue2(this.venue2)
                .venue3(this.venue3)
                .sacPlanned(this.sacPlanned)
                .eventTitle(this.eventTitle)
                .host(this.host)
                .organizer(this.organizer)
                .sponsor(this.sponsor)
                .requiredAge(this.requiredAge)
                .onSale(this.onSale)
                .pricingPolicy(this.pricingPolicy)
                .poster(this.poster)
                .detail_img(this.detail_img)
                .contact(this.contact)
                .openDate(this.openDate)
                .finDate(this.finDate)
                .openTime(this.openTime)
                .lastEntrance(this.lastEntrance)
                .closeTime(this.closeTime)
                .eventTime(this.eventTime)
                .runningTime(this.runningTime)
                .eventNews(this.eventNews)
                .build();
    }

    public EventLD toListDto() {
        return EventLD.builder()
                .id(this.id)
                .eventGroup(this.eventGroup)
                .venue1(this.venue1)
                .venue2(this.venue2)
                .venue3(this.venue3)
                .sacPlanned(this.sacPlanned)
                .eventTitle(this.eventTitle)
                .onSale(this.onSale)
                .openDate(this.openDate)
                .finDate(this.finDate)
                .eventTime(this.getEventTime())
                .build();
    }

    public void addPricingPolicy(PricingPolicy a) {
        System.out.println(this.getPricingPolicy());
        this.pricingPolicy.add(a);
        a.setEvent(this);
    }

    public void removePricingPolicy(PricingPolicy a) {
        System.out.println(this.getPricingPolicy());
        a.setEvent(null);
        this.pricingPolicy.remove(a);
    }

    public void addEventDetailImg(EventDetailImg a) {
        this.detail_img.add(a);
    }

    public void removePoster() {
        UpAndDownFile.deletePosterFile(this.getPoster());
        this.poster = null;
    }
}
