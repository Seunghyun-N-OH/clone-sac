package com.example.sac.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.example.sac.domain.entities.AttachedFile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AttachedFileR extends JpaRepository<AttachedFile, Long> {

    List<AttachedFile> findByNoticeNo(long notice_no);

    Optional<AttachedFile> findByFnoAndNoticeNo(long fno, long notice_id);

    @Transactional
    void deleteByNoticeNo(long notice_number);

}
