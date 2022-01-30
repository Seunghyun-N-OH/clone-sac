package com.example.sac.web.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventLD {
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
    private char onSale; // [s, o, p, c, i]
    // 입장권 판매상태 (s)ite, (o)nline, (p)lanned, (c)losed, (i)nvitational

    // [일정] 행사가 기간으로 진행되는 경우 입력 ##################################################
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate; // [2022-01-27]
    // 행사 개막일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finDate; // [2022-01-29]
    // 행사 폐막일

    // [일정] 행사가 회차별로 진행되는 경우 입력 #################################################
    // "${#dates.format(boardVO.regdate, 'yyyy-MM-dd HH:mm')}"
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    // TODO 날짜+시간형식이 안이뻐서 html상에서 받는 방식을 바꿀지 고려
    private List<LocalDateTime> eventTime; // [2022-01-27 12:00]
    // 회차별 시작시간
}
