package com.example.sac.domain.repositories;

import com.example.sac.domain.entities.Complaints;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintsR extends JpaRepository<Complaints, Long> {

}
