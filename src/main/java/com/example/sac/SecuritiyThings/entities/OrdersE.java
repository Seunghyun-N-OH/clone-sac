package com.example.sac.SecuritiyThings.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.sac.SecuritiyThings.dtos.OrdersD;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class OrdersE {
    @Id
    @GeneratedValue
    private long id;

    private String merchantUid;
    private String payMethod;
    private int paidAmount;
    private String name;
    private String buyerEmail;
    private LocalDate effectiveDate;
    private LocalDate expiryDate;

    private String buyerUserId;

    @CreatedDate
    private LocalDateTime cDateTime;

    public OrdersD toDto() {

        String a;

        System.out.println(this.expiryDate.compareTo(LocalDate.now()));

        if (this.expiryDate.compareTo(LocalDate.now()) >= 0) {
            a = "정상";
        } else {
            a = "만료";
        }

        return OrdersD.builder()
                .id(this.id)
                .merchantUid(this.merchantUid)
                .payMethod(this.payMethod)
                .paidAmount(this.paidAmount)
                .name(this.name)
                .buyerEmail(this.buyerEmail)
                .effectiveDate(this.effectiveDate)
                .expiryDate(this.expiryDate)
                .isValid(a)
                .buyerUserId(this.buyerUserId)
                .build();
    }

    public static String generateUid(String product, String userId) {
        return LocalDate.now().getYear() + LocalDate.now().getMonthValue() + LocalDate.now().getDayOfMonth() + "-"
                + product + "-" + userId + LocalDateTime.now().getMinute()
                + LocalDateTime.now().getNano();
    }
}
