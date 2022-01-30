package com.example.sac.web.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.sac.domain.entities.EventDetailImg;
import com.example.sac.domain.entities.EventPoster;
import com.example.sac.domain.entities.PricingPolicy;

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
public class EventD {
    private long id; // [1, 2, 3, 4 ...]
    // 행사마다 부여되는 id
    private String eventGroup; // [exhibition, show]
    // 전시인지 공연인지 구분
    private String venue1; // [음악당, 오페라하우스, 전시장]
    // 진행장소 대분류
    private String venue2; // [한가람디자인미술관, 한가람미술관]
    // 진행장소 중분류
    private String venue3; // [1층, 2층, 특별관]
    // 진행장소 소분류
    private char sacPlanned; // [c, y, n]
    // sac 기획행사인지여부
    private String eventTitle; // [라이프사진전, 샤갈달리뷔페]
    // 행사 명
    private List<String> host; // [동아일보]
    // 주관
    private List<String> organizer; // [영국문화원]
    // 주최
    private List<String> sponsor; // [외교부]
    // 후원/협찬
    private String requiredAge; // [7, 18 ...]
    // 입장/관람 가능 나이제한
    private char onSale; // [s, o, p, c, i]
    // 입장권 판매상태 (s)ite, (o)nline, (p)lanned, (c)losed, (i)nvitational

    private List<PricingPolicy> pricingPolicy; // [성인-15000, 청소년-12000 ...]
    // 가격정책

    private EventPoster poster; // TODO 파일 받을 때 이미지파일 맞는지 타입체크 필요
    // 포스터

    private List<EventDetailImg> detail_img; // TODO 파일 받을 때 이미지파일 맞는지 타입체크 필요
    // 상세페이지

    private String contact; // [010-5516-2461]
    // 문의전화

    // [일정] 행사가 기간으로 진행되는 경우 입력 ##################################################
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate; // [2022-01-27]
    // 행사 개막일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finDate; // [2022-01-29]
    // 행사 폐막일
    private String openTime; // [12:00]
    // 일별 행사 오픈시간
    private String lastEntrance; // [17:00]
    // 일별 행사 마감시간
    private String closeTime; // [18:00]
    // 일별 행사 종료시간

    // [일정] 행사가 회차별로 진행되는 경우 입력 #################################################
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private List<LocalDateTime> eventTime; // [2022-01-27 12:00]
    // 회차별 시작시간
    private int runningTime; // [90, 100, 180 ...]
    // 러닝타임
}
