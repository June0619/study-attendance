package com.spring.attandance.repository;

import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.StudyGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Commit
public class JpaStudyGroupRepositoryTest {

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("[통합] 스터디 그룹 저장")
    void save() {
        // given
        Member testUser = new Member("test_user", "01012345678");
        em.persist(testUser);

        StudyGroup studyGroup = new StudyGroup("TEST_GROUP", testUser);

        // when
        studyGroupRepository.save(studyGroup);
        Optional<StudyGroup> findStudyGroup = studyGroupRepository.findById(studyGroup.getId());

        // then
        assertThat(findStudyGroup.get()).isEqualTo(studyGroup);
    }
}
