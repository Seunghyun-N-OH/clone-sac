package com.example.sac.web.dtos;

import com.example.sac.domain.entities.PricingPolicyE;

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
public class PricingPolicyD {
    private long priceId;
    private String subject;
    private int price;

    public PricingPolicyE toEntity() {
        return PricingPolicyE.builder()
                .priceId(this.getPriceId())
                .subject(this.getSubject())
                .price(this.getPrice())
                .build();
    }
}
