package com.spring.attandance.controller;

import com.spring.attandance.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("local")
@Component
@RequiredArgsConstructor
public class Init {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.init();
    }

    @Component
    static class InitMemberService {
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            for (int i = 0; i < 100; i++) {
                Member member = Member.builder()
                        .name("test" + i)
                        .mobile("010123412" + String.format("%02d", i))
                        .email("test" + i + "@test.com")
                        .build();
                em.persist(member);
            }
        }
    }

}
