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

            for (int i = 0; i < 3; i++) {

                Group group = Group.builder()
                        .name("group_" + (i+1))
                        .build();

                for (int j = 1; j <= 5; j++) {

                    int count = i * 5 + j;

                    Member member = Member.builder()
                            .name("test" + count)
                            .mobile("010123412" + String.format("%02d", count))
                            .email("test" + count + "@test.com")
                            .build();

                    em.persist(member);

                    if(j % 5 == 1) {
                        group.changeMaster(member);
                        em.persist(group);

                        GroupMember groupMember = GroupMember.builder()
                                .group(group)
                                .member(member)
                                .role(GroupRole.MASTER)
                                .build();

                        em.persist(groupMember);
                    } else if(j % 5 == 2) {
                        GroupMember groupMember = GroupMember.builder()
                                .group(group)
                                .member(member)
                                .role(GroupRole.ADMIN)
                                .build();

                        em.persist(groupMember);
                    } else {
                        GroupMember groupMember = GroupMember.builder()
                                .group(group)
                                .member(member)
                                .role(GroupRole.MEMBER)
                                .build();

                        em.persist(groupMember);
                    }
                }
            }
        }
    }
}
