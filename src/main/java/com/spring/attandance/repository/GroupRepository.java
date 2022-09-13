package com.spring.attandance.repository;

import com.spring.attandance.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMasterId(Long id);
    int countGroupsByMasterId(Long id);
}
