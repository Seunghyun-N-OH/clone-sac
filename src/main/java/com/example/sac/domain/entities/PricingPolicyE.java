package com.example.sac.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.sac.web.dtos.PricingPolicyD;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PricingPolicyE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long priceId;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "evente_id")
    private EventE event;

    public PricingPolicyD toDto() {
        return PricingPolicyD.builder()
                .priceId(this.getPriceId())
                .subject(this.getSubject())
                .price(this.getPrice())
                .build();
    }
}
