package com.spring.attandance.repository;

import com.spring.attandance.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    public void save(Member member);
    public Optional<Member> findOne(Long userId);
    public List<Member> findList();

}
