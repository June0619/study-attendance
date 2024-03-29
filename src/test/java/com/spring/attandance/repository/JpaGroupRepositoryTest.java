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

        Group group = Group.builder()
                .name("TEST_GROUP")
                .master(testUser)
                .build();

        // when
        groupRepository.save(group);
        Optional<Group> findGroup = groupRepository.findById(group.getId());

        // then
        assertThat(findGroup.get()).isEqualTo(group);
    }

    @Test
    @DisplayName("[통합] 소유자 아이디 카운트 조회")
    void findByMasterId() {
        // given
        Member testUser = Member.builder()
                .name("test_user")
                .build();

        Group group = Group.builder()
                .name("TEST_GROUP")
                .master(testUser)
                .build();

        em.persist(testUser);
        em.persist(group);

        // when
        int result = groupRepository.countGroupsByMasterId(testUser.getId());

        // then
        assertThat(result).isEqualTo(1);
    }
}
