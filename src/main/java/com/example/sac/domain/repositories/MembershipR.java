package com.example.sac.domain.repositories;

import com.example.sac.SecuritiyThings.entities.Membership;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipR extends JpaRepository<Membership, String> {
}
