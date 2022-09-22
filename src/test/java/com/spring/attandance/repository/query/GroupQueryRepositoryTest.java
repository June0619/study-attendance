package com.spring.attandance.repository.query;

import com.spring.attandance.controller.dto.group.GroupResponseDTO;
import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.cond.GroupSearchCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class GroupQueryRepositoryTest {

    @Autowired EntityManager em;
    @Autowired GroupQueryRepository groupQueryRepository;

    @Test
    @DisplayName("[통합] GroupQueryRepository.searchGroupList")
    void list() {
        //given
        Group g1 = Group.builder()
                .name("group1")
                .build();

        Group g2 = Group.builder()
                .name("group2")
                .build();

        em.persist(g1);
        em.persist(g2);

        Member m1 = Member.builder()
                .name("member1")
                .build();

        Member m2 = Member.builder()
                .name("member2")
                .build();

        Member m3 = Member.builder()
                .name("member3")
                .build();

        Member m4 = Member.builder()
                .name("member4")
                .build();

        em.persist(m1);
        em.persist(m2);
        em.persist(m3);
        em.persist(m4);

        GroupMember gm1 = GroupMember.builder()
                .group(g1)
                .member(m1)
                .build();

        GroupMember gm2 = GroupMember.builder()
                .group(g1)
                .member(m2)
                .build();

        GroupMember gm3 = GroupMember.builder()
                .group(g2)
                .member(m3)
                .build();

        GroupMember gm4 = GroupMember.builder()
                .group(g2)
                .member(m4)
                .build();

        em.persist(gm1);
        em.persist(gm2);
        em.persist(gm3);
        em.persist(gm4);

        em.flush();
        em.clear();

        //when
        Page<GroupResponseDTO> allResult = groupQueryRepository.searchGroupList(new GroupSearchCondition(), PageRequest.of(0, 10));
        Page<GroupResponseDTO> nameResult = groupQueryRepository.searchGroupList(new GroupSearchCondition("group1"), PageRequest.of(0, 10));

        //then
        assertEquals(allResult.getTotalElements(), 2);
        assertEquals(nameResult.getTotalElements(), 1);
        assertThat(nameResult.getContent().get(0).getName()).isEqualTo("group1");
    }

}