package com.example.sac.domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Column(nullable = false)
    private String eventTitle; // [라이프사진전, 샤갈달리뷔페]
    // 행사 명
    @ElementCollection
    @CollectionTable(name = "host")
    @Builder.Default
    private List<String> host = new ArrayList<>(); // [동아일보]
    // 주관
    @ElementCollection
    @CollectionTable(name = "organizer")
    @Builder.Default
    private List<String> organizer = new ArrayList<>(); // [영국문화원]
    // 주최
    @ElementCollection
    @CollectionTable(name = "sponsor")
    @Builder.Default
    private List<String> sponsor = new ArrayList<>(); // [외교부]
    // 후원/협찬
    @Column(nullable = true)
    private String requiredAge; // [7, 18 ...]
    // 입장/관람 가능 나이제한
    @Column(nullable = false)
    private char onSale; // [s, o, p, c, i]
    // 입장권 판매상태 (s)ite, (o)nline, (p)lanned, (c)losed, (i)nvitational

    @OneToMany(mappedBy = "event")
    @Builder.Default // 양방향 1:N
    private List<PricingPolicyE> pricingPolicy = new ArrayList<>(); // [성인-15000, 청소년-12000 ...]
    // 가격정책

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "poster_id") // 단방향 1:1 [EventImageE 는 FK 안가지고, EventE 만 FK 가짐]
    private EventImageE poster; // TODO 파일 받을 때 이미지파일 맞는지 타입체크 필요
    // 포스터

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id") // 단방향 1:1 [EventImageE 는 FK 안가지고, EventE 만 FK 가짐]
    private EventImageE detailImage; // TODO 파일 받을 때 이미지파일 맞는지 타입체크 필요
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

    public EventD toDto() {
        if (this.getPoster() == null && this.getDetailImage() == null) {
            System.out.println("Poster & Detail are null");
            return EventD.builder()
                    .id(this.getId())
                    .eventGroup(this.getEventGroup())
                    .venue1(this.getVenue1())
                    .venue2(this.getVenue2())
                    .venue3(this.getVenue3())
                    .sacPlanned(this.getSacPlanned())
                    .eventTitle(this.getEventTitle())
                    .host(this.getHost())
                    .organizer(this.getOrganizer())
                    .sponsor(this.getSponsor())
                    .requiredAge(this.getRequiredAge())
                    .onSale(this.getOnSale())
                    .pricingPolicy(this.getPricingPolicy().stream().map(b -> b.toDto()).collect(Collectors.toList()))
                    .contact(this.getContact())
                    .openDate(this.getOpenDate())
                    .finDate(this.getFinDate())
                    .openTime(this.getOpenTime())
                    .lastEntrance(this.getLastEntrance())
                    .closeTime(this.getCloseTime())
                    .eventTime(this.getEventTime())
                    .runningTime(this.getRunningTime())
                    .build();
        } else if (this.getPoster() == null && this.getDetailImage() != null) {
            System.out.println("Poster is null, but not Detail");
            return EventD.builder()
                    .id(this.getId())
                    .eventGroup(this.getEventGroup())
                    .venue1(this.getVenue1())
                    .venue2(this.getVenue2())
                    .venue3(this.getVenue3())
                    .sacPlanned(this.getSacPlanned())
                    .eventTitle(this.getEventTitle())
                    .host(this.getHost())
                    .organizer(this.getOrganizer())
                    .sponsor(this.getSponsor())
                    .requiredAge(this.getRequiredAge())
                    .onSale(this.getOnSale())
                    .pricingPolicy(this.getPricingPolicy().stream().map(b -> b.toDto()).collect(Collectors.toList()))
                    .detailImage(this.getDetailImage().toDto())
                    .contact(this.getContact())
                    .openDate(this.getOpenDate())
                    .finDate(this.getFinDate())
                    .openTime(this.getOpenTime())
                    .lastEntrance(this.getLastEntrance())
                    .closeTime(this.getCloseTime())
                    .eventTime(this.getEventTime())
                    .runningTime(this.getRunningTime())
                    .build();
        } else if (this.getPoster() != null && this.getDetailImage() == null) {
            System.out.println("Poster is not null, but Detail is");
            return EventD.builder()
                    .id(this.getId())
                    .eventGroup(this.getEventGroup())
                    .venue1(this.getVenue1())
                    .venue2(this.getVenue2())
                    .venue3(this.getVenue3())
                    .sacPlanned(this.getSacPlanned())
                    .eventTitle(this.getEventTitle())
                    .host(this.getHost())
                    .organizer(this.getOrganizer())
                    .sponsor(this.getSponsor())
                    .requiredAge(this.getRequiredAge())
                    .onSale(this.getOnSale())
                    .pricingPolicy(this.getPricingPolicy().stream().map(b -> b.toDto()).collect(Collectors.toList()))
                    .poster(this.getPoster().toDto())
                    .contact(this.getContact())
                    .openDate(this.getOpenDate())
                    .finDate(this.getFinDate())
                    .openTime(this.getOpenTime())
                    .lastEntrance(this.getLastEntrance())
                    .closeTime(this.getCloseTime())
                    .eventTime(this.getEventTime())
                    .runningTime(this.getRunningTime())
                    .build();
        } else {
            System.out.println("Both Poster and Detail are not null");
            return EventD.builder()
                    .id(this.getId())
                    .eventGroup(this.getEventGroup())
                    .venue1(this.getVenue1())
                    .venue2(this.getVenue2())
                    .venue3(this.getVenue3())
                    .sacPlanned(this.getSacPlanned())
                    .eventTitle(this.getEventTitle())
                    .host(this.getHost())
                    .organizer(this.getOrganizer())
                    .sponsor(this.getSponsor())
                    .requiredAge(this.getRequiredAge())
                    .onSale(this.getOnSale())
                    .pricingPolicy(this.getPricingPolicy().stream().map(b -> b.toDto()).collect(Collectors.toList()))
                    .poster(this.getPoster().toDto())
                    .detailImage(this.getDetailImage().toDto())
                    .contact(this.getContact())
                    .openDate(this.getOpenDate())
                    .finDate(this.getFinDate())
                    .openTime(this.getOpenTime())
                    .lastEntrance(this.getLastEntrance())
                    .closeTime(this.getCloseTime())
                    .eventTime(this.getEventTime())
                    .runningTime(this.getRunningTime())
                    .build();
        }
    }

    public EventLD toLDto() {
        if (this.getEventGroup().equals("exhibition")) {
            return EventLD.builder()
                    .id(this.getId())
                    .eventGroup(this.getEventGroup())
                    .venue1(this.getVenue1() + this.getVenue2() + this.getVenue3())
                    .sacPlanned(this.getSacPlanned())
                    .eventTitle(this.getEventTitle())
                    .onSale(this.getOnSale())
                    .openDate(this.getOpenDate())
                    .finDate(this.getFinDate())
                    .build();
        } else {
            return EventLD.builder()
                    .id(this.getId())
                    .eventGroup(this.getEventGroup())
                    .venue1(this.getVenue1() + this.getVenue2() + this.getVenue3())
                    .sacPlanned(this.getSacPlanned())
                    .eventTitle(this.getEventTitle())
                    .onSale(this.getOnSale())
                    .eventTime(this.getEventTime())
                    .build();
        }
    }
}
