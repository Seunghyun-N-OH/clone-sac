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
    // Lazy한 첨부파일친구도 같이 가져가기

    List<NoticeE> findTop8ByOrderByEffectiveDateBDesc();
    // 효력시작일 채-신 8개만 가져가자
}
