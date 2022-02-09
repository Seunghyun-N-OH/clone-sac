package com.example.sac.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.sac.web.dtos.EventD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketHistory {
    @Id
    @GeneratedValue
    private long id;

    private long eventId;
    private String eventName;
    private String ticketClass;
    private int ticketPrice;
    private String isUsage; // 사용완료, 미사용, 환불, 취소
    private String isExpired; // 만료, 사용가능, 사용불가
    private String buyerId; // userId
    private LocalDateTime validDateTime; // 해당 행사 시작시간/또는 종료시간
    private LocalDateTime purchasedAt; // 구매(row 저장)시간
    private LocalDateTime usedAt; // 사용처리 시간

    public TicketHistory(EventD a, String user, String ticketClass, int ticketPrice, LocalDateTime showTime) {
        this.eventId = a.getId();
        this.eventName = a.getEventTitle();
        this.ticketClass = ticketClass;
        this.ticketPrice = ticketPrice;
        this.isUsage = "미사용";
        this.isExpired = "사용가능";
        this.buyerId = user;
        this.validDateTime = showTime;
        this.purchasedAt = LocalDateTime.now();
        this.usedAt = null;
    }
}
