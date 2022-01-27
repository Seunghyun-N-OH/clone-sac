package com.example.sac.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.example.sac.domain.entities.AttachedFileE;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AttachedFileR extends JpaRepository<AttachedFileE, Long> {

    List<AttachedFileE> findByNoticeNo(long notice_number);

    Optional<AttachedFileE> findByFnoAndNoticeNo(long fno, long notice_number);

    @Transactional
    void deleteByNoticeNo(long notice_number);

}
