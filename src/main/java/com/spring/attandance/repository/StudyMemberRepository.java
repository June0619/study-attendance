package com.spring.attandance.repository;

import com.spring.attandance.domain.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    List<StudyMember> findByJoinMemberId(Long memberId);
}
