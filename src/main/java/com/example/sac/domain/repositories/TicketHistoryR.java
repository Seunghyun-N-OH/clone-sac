package com.example.sac.domain.repositories;

import com.example.sac.domain.entities.TicketHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketHistoryR extends JpaRepository<TicketHistory, Long> {

}
