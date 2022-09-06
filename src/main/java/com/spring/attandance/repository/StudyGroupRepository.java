package com.spring.attandance.repository;

import com.spring.attandance.domain.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    List<StudyGroup> findByMasterId(Long id);
}
