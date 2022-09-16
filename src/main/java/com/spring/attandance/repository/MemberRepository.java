package com.spring.attandance.repository;

import com.spring.attandance.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @EntityGraph(attributePaths = {"groupMembers", "groupMembers.group"})
    Optional<Member> findMemberByMobile(String mobile);
}
