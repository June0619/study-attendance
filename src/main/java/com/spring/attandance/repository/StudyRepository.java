package com.spring.attandance.repository;

import com.spring.attandance.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByOwnerId(Long id);
}
