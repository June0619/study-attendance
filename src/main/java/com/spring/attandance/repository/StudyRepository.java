package com.spring.attandance.repository;

import com.spring.attandance.domain.Study;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByOwnerId(Long id);
    @EntityGraph(attributePaths = {"owner", "group"})
    Optional<Study> findById(Long id);

}
