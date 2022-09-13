package com.spring.attandance.repository;

import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.Member;
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
public class JpaGroupRepositoryTest {

    @Autowired
    GroupRepository groupRepository;

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

        Group group = new Group("TEST_GROUP", testUser);

        // when
        groupRepository.save(group);
        Optional<Group> findGroup = groupRepository.findById(group.getId());

        // then
        assertThat(findGroup.get()).isEqualTo(group);
    }

    @Test
    @DisplayName("[통합] 스터디 그룹 소유자 아이디로 조회")
    void findByMasterId() {
        // given
        Member testUser = Member.builder()
                .name("test_user")
                .build();
        Group group = new Group("TEST_GROUP", testUser);

        em.persist(testUser);
        em.persist(group);

        // when
        List<Group> result = groupRepository.findByMasterId(testUser.getId());

        // then
        assertThat(result.size()).isEqualTo(1);
        assertThat(result).extracting("name").containsExactly("TEST_GROUP");
    }
}
