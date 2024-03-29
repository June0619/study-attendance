package com.spring.attandance.repository;

import com.spring.attandance.domain.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    long countGroupsByMemberId(Long memberId);
    Optional<GroupMember> findByMemberIdAndGroupId(Long memberId, Long groupId);
}
