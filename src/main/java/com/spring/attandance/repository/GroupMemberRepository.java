package com.spring.attandance.repository;

import com.spring.attandance.domain.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
