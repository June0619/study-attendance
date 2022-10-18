package com.spring.attandance.repository;

import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.Study;
import com.spring.attandance.domain.StudyMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class StudyMemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    StudyMemberRepository studyMemberRepository;

    @Test
    @DisplayName("[통합] 참여자 기준으로 검색")
    void findByMemberId() {
        //given

        Member m1 = Member.builder()
                .name("test_user")
                .build();

        Study s1 = Study.builder().build();

        StudyMember studyMember = StudyMember.builder()
                .joinMember(m1)
                .study(s1)
                .build();

        em.persist(m1);
        em.persist(s1);
        em.persist(studyMember);

        //when
        StudyMember findStudyMember = studyMemberRepository.findByJoinMemberId(m1.getId()).get(0);

        //then
        assertThat(findStudyMember).isNotNull();
        assertThat(findStudyMember.getJoinMember()).isEqualTo(m1);
        assertThat(findStudyMember.getStudy()).isEqualTo(s1);

    }

}