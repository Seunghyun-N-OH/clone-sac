package com.example.sac.domain.repositories;

import com.example.sac.domain.entities.PricingPolicy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingPolicyR extends JpaRepository<PricingPolicy, Long> {

}
