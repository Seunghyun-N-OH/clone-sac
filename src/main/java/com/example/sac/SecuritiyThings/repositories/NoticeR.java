package com.example.sac.SecuritiyThings.repositories;

import java.util.List;

import com.example.sac.domain.entities.NoticeE;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeR extends JpaRepository<NoticeE, Long> {

    List<NoticeE> findByImportantOrderByEffectiveDateBDesc(char important);

    List<NoticeE> findByCategoryOrderByEffectiveDateBDesc(String cat);

    List<NoticeE> findByContentContainingOrTitleContainingOrderByEffectiveDateBDesc(String content, String title);

    List<NoticeE> findByContentContainingOrderByEffectiveDateBDesc(String content);

    List<NoticeE> findByTitleContainingOrderByEffectiveDateBDesc(String title);

}
