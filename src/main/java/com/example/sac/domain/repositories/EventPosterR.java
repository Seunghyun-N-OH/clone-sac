package com.example.sac.domain.repositories;

import com.example.sac.domain.entities.EventPoster;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPosterR extends JpaRepository<EventPoster, Long> {

}
