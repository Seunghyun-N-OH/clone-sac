package com.example.sac.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.example.sac.domain.entities.NoticeE;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeR extends JpaRepository<NoticeE, Long> {

    List<NoticeE> findByImportantOrderByEffectiveDateBDesc(char important);

    List<NoticeE> findByCategoryOrderByEffectiveDateBDesc(String cat);

    List<NoticeE> findByContentContainingOrTitleContainingOrderByEffectiveDateBDesc(String content, String title);

    List<NoticeE> findByContentContainingOrderByEffectiveDateBDesc(String content);

    List<NoticeE> findByTitleContainingOrderByEffectiveDateBDesc(String title);

    @EntityGraph(attributePaths = { "attachment" }, type = EntityGraph.EntityGraphType.LOAD)
    Optional<NoticeE> findWithAttachmentByNo(Long userId);
    // 첨부파일도 함께 가져갑시다

    List<NoticeE> findTop8ByOrderByEffectiveDateBDesc();
    // 최신 8개만 찾아가야지..
}
