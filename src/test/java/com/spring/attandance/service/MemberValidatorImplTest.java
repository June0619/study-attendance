package com.spring.attandance.service;

import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.Study;
import com.spring.attandance.domain.cond.MemberSearchCondition;
import com.spring.attandance.domain.cond.StudySearchCondition;
import com.spring.attandance.domain.enums.PassedStudy;
import com.spring.attandance.repository.GroupRepository;
import com.spring.attandance.repository.query.MemberQueryRepository;
import com.spring.attandance.repository.query.StudyQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberValidatorImplTest {

    @Mock
    MemberQueryRepository memberQueryRepository;
    @Mock
    GroupRepository groupRepository;
    @Mock
    StudyQueryRepository studyQueryRepository;
    @InjectMocks
    MemberValidatorImpl validator;

    @Test
    @DisplayName("[단위] 중복 체크 - 성공")
    void notDuplicated() {
        //given
        MemberSearchCondition cond = MemberSearchCondition.builder()
                .mobile("01056781234")
                .build();

        doReturn(Optional.empty()).when(memberQueryRepository).searchMember(cond);

        Member member = Member.builder()
                .mobile("01056781234")
                .build();

        //then
        assertDoesNotThrow(() -> validator.duplicateCheck(member));
    }

    @Test
    @DisplayName("[단위] 중복 체크 - 실패")
    void duplicated() {
        //given
        Member givenMember = Member.builder()
                .name("test_member")
                .mobile("01012345678")
                .build();

        MemberSearchCondition cond = MemberSearchCondition.builder()
                .mobile("01012345678")
                .build();

        doReturn(Optional.of(givenMember)).when(memberQueryRepository).searchMember(cond);

        //when
        Member member = Member.builder()
                .mobile("01012345678")
                .build();

        //then
        assertThrows(IllegalStateException.class, () -> validator.duplicateCheck(member));
    }

    @Test
    @DisplayName("[단위] 소유 스터디 체크 - 성공")
    void studyOpen() {
        //given
        List<Study> result = List.of();

        StudySearchCondition cond = StudySearchCondition.builder()
                .ownerId(1L)
                .passedStudy(PassedStudy.NOT_PASSED)
                .build();

        //when
        doReturn(result).when(studyQueryRepository).searchStudy(cond);

        //then
        assertDoesNotThrow(() -> validator.openStudyCheck(1L));
    }

    @Test
    @DisplayName("[단위] 소유 스터디 체크 - 실패")
    void notStudyOpen() {
        //given
        List<Study> result = List.of(
                Study.builder()
                        .startTime(LocalDateTime.now().plusHours(1))
                        .endTime(LocalDateTime.now().plusHours(2))
                        .build()
        );

        StudySearchCondition cond = StudySearchCondition.builder()
                .ownerId(1L)
                .passedStudy(PassedStudy.NOT_PASSED)
                .build();

        //when
        doReturn(result).when(studyQueryRepository).searchStudy(cond);

        //then
        assertThrows(IllegalStateException.class, () -> validator.openStudyCheck(1L));
    }

    @Test
    @DisplayName("[단위] 스터디 그룹 소유 체크 - 성공")
    void studyGroupOwn () {
        //given
        List<Group> result = List.of();

        //when
        doReturn(result).when(groupRepository).findByMasterId(1L);

        //then
        assertDoesNotThrow(() -> validator.studyGroupOwnerCheck(1L));
    }

    @Test
    @DisplayName("[단위] 스터디 그룹 소유 체크 - 실패")
    void notStudyGroupOwn() {
        //given
        List<Group> result = List.of(
                Group.builder()
                        .build()
        );

        //when
        doReturn(result).when(groupRepository).findByMasterId(1L);

        //then
        assertThrows(IllegalStateException.class, () -> validator.studyGroupOwnerCheck(1L));
    }
}