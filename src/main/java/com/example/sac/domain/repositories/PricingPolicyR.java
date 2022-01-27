package com.example.sac.domain.repositories;

import com.example.sac.domain.entities.PricingPolicyE;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingPolicyR extends JpaRepository<PricingPolicyE, Long> {

}
