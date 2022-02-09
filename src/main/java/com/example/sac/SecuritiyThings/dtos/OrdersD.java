package com.example.sac.SecuritiyThings.dtos;

import java.time.LocalDate;

import com.example.sac.SecuritiyThings.entities.OrdersE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class OrdersD {
    private long id;
    private String merchantUid;
    private String payMethod;
    private int paidAmount;
    private String name;
    private String buyerEmail;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;
    private String isValid; // 정상 or 만료

    private String buyerUserId;

    public OrdersE toSaveEntity() {
        return OrdersE.builder()
                .merchantUid(this.merchantUid)
                .payMethod(this.payMethod)
                .paidAmount(this.paidAmount)
                .name(this.name)
                .buyerEmail(this.buyerEmail)
                .effectiveDate(this.effectiveDate)
                .expiryDate(this.expiryDate)
                .buyerUserId(this.buyerUserId)
                .build();
    }
}
