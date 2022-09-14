package com.spring.attandance.repository;

import com.spring.attandance.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    int countGroupsByMasterId(Long id);
}
