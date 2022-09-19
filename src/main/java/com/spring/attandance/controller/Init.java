package com.spring.attandance.controller;

import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.enums.GroupRole;
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
            for (int i = 1; i <= 20; i++) {
                Member member = Member.builder()
                        .name("test" + i)
                        .mobile("010123412" + String.format("%02d", i))
                        .email("test" + i + "@test.com")
                        .build();
                em.persist(member);

                long groupId = 0;

                if (i % 7 == 1) {
                    Group group = Group.builder()
                            .name("group" + (i/7 + 1))
                            .master(member)
                            .build();
                    em.persist(group);

                    GroupMember groupMember = GroupMember.builder()
                            .group(group)
                            .member(member)
                            .role(GroupRole.MASTER)
                            .build();

                    em.persist(groupMember);
                }
            }
        }
    }

}
