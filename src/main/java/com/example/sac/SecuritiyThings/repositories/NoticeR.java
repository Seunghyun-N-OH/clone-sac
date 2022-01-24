package com.example.sac.SecuritiyThings.repositories;

import java.util.List;

import com.example.sac.SecuritiyThings.entities.Notice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeR extends JpaRepository<Notice, Long> {

    List<Notice> findByImportantOrderByNoDesc(char important);

}
