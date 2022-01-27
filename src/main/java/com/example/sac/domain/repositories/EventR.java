package com.example.sac.domain.repositories;

import com.example.sac.domain.entities.EventE;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventR extends JpaRepository<EventE, Long> {

}
