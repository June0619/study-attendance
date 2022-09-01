package com.spring.attandance.repository;

import com.spring.attandance.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Optional<Member> findOne(Long userId) {
        return Optional.of(em.find(Member.class, userId));
    }

    @Override
    public List<Member> findList() {
        return em.createQuery("select u from Member u", Member.class)
                .getResultList();
    }
}
