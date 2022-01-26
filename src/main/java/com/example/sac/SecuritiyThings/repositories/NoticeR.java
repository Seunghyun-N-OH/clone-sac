package com.example.sac.SecuritiyThings.repositories;

import java.util.List;

import com.example.sac.domain.entities.Notice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeR extends JpaRepository<Notice, Long> {

    List<Notice> findByImportantOrderByEffectiveDateBDesc(char important);

    List<Notice> findByCategoryOrderByEffectiveDateBDesc(String cat);

    List<Notice> findByContentContainingOrTitleContainingOrderByEffectiveDateBDesc(String content, String title);

    List<Notice> findByContentContainingOrderByEffectiveDateBDesc(String content);

    List<Notice> findByTitleContainingOrderByEffectiveDateBDesc(String title);

}
