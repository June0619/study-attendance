package com.spring.attandance.repository.query;

import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.Study;
import com.spring.attandance.domain.cond.StudySearchCondition;
import com.spring.attandance.repository.query.StudyQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static com.spring.attandance.domain.enums.PassedStudy.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudyQueryRepositoryTest {

    @Autowired EntityManager em;
    @Autowired
    StudyQueryRepository studyQueryRepository;

    @Test
    @DisplayName("[통합] 스터디 검색 - 시간 기준")
    void searchStudyByPassed() {
        //given
        Member member = Member.builder().name("member").build();
        em.persist(member);

        //현재 시각-2 ~ 현재 시각-1
        Study passedStudy = new Study(member, LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(1));
        //현재 시각-1 ~ 현재 시각+1
        Study ongoingStudy = new Study(member, LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1));
        //현재 시각+1 ~ 현재 시각+2
        Study futureStudy = new Study(member, LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));

        em.persist(passedStudy);
        em.persist(ongoingStudy);
        em.persist(futureStudy);

        StudySearchCondition passedCond = StudySearchCondition.builder()
                .passedStudy(PASSED)
                .build();

        StudySearchCondition ongoingCond = StudySearchCondition.builder()
                .passedStudy(ON_GOING)
                .build();

        StudySearchCondition futureCond = StudySearchCondition.builder()
                .passedStudy(NOT_OPENED)
                .build();

        //when
        List<Study> passedStudyList = studyQueryRepository.searchStudy(passedCond);
        List<Study> ongoingStudyList = studyQueryRepository.searchStudy(ongoingCond);
        List<Study> futureStudyList = studyQueryRepository.searchStudy(futureCond);

        //then
        assertThat(passedStudyList.size()).isEqualTo(1);
        assertThat(passedStudyList).containsExactly(passedStudy);
        assertThat(ongoingStudyList.size()).isEqualTo(1);
        assertThat(ongoingStudyList).containsExactly(ongoingStudy);
        assertThat(futureStudyList.size()).isEqualTo(1);
        assertThat(futureStudyList).containsExactly(futureStudy);
    }

}