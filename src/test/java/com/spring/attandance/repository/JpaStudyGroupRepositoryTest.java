package com.spring.attandance.repository;

import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.StudyGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class JpaStudyGroupRepositoryTest {

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("[통합] 스터디 그룹 저장")
    void save() {
        // given
        Member testUser = Member.builder()
                .name("test_user")
                .mobile("01012345678")
                .build();

        em.persist(testUser);

        StudyGroup studyGroup = new StudyGroup("TEST_GROUP", testUser);

        // when
        studyGroupRepository.save(studyGroup);
        Optional<StudyGroup> findStudyGroup = studyGroupRepository.findById(studyGroup.getId());

        // then
        assertThat(findStudyGroup.get()).isEqualTo(studyGroup);
    }

    @Test
    @DisplayName("[통합] 스터디 그룹 소유자 아이디로 조회")
    void findByMasterId() {
        // given
        Member testUser = Member.builder()
                .name("test_user")
                .build();
        StudyGroup studyGroup = new StudyGroup("TEST_GROUP", testUser);

        em.persist(testUser);
        em.persist(studyGroup);

        // when
        List<StudyGroup> result = studyGroupRepository.findByMasterId(testUser.getId());

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).extracting("name").containsExactly("TEST_GROUP");
    }
}
