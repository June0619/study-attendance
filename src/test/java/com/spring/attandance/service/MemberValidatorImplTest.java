package com.spring.attandance.service;

import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.cond.MemberSearchCondition;
import com.spring.attandance.repository.MemberQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberValidatorImplTest {

    @Mock
    MemberQueryRepository repository;
    @InjectMocks
    MemberValidatorImpl validator;

    @Test
    @DisplayName("[단위] validator 중복_성공")
    void notDuplicated() {
        //given
        MemberSearchCondition cond = MemberSearchCondition.builder()
                .mobile("01056781234")
                .build();

        doReturn(Optional.empty()).when(repository).searchMember(cond);

        Member member = Member.builder()
                .mobile("01056781234")
                .build();

        //then
        assertDoesNotThrow(() -> validator.duplicateCheck(member));
    }

    @Test
    @DisplayName("[단위] validator 중복_실패")
    void duplicated() {
        //given
        Member givenMember = Member.builder()
                .name("test_member")
                .mobile("01012345678")
                .build();

        MemberSearchCondition cond = MemberSearchCondition.builder()
                .mobile("01012345678")
                .build();

        doReturn(Optional.of(givenMember)).when(repository).searchMember(cond);

        //when
        Member member = Member.builder()
                .mobile("01012345678")
                .build();

        //then
        assertThrows(IllegalStateException.class, () -> validator.duplicateCheck(member));
    }

    //TODO: 이하 테스트 작성

    @Test
    @DisplayName("[단위] 소유 스터디 체크 - 성공")
    void studyOwn() {

    }

    @Test
    @DisplayName("[단위] 소유 스터디 체크 - 실패")
    void notStudyOwn() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("[통합] 스터디 그룹 소유 체크 - 성공")
    void studyGroupOwn () {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("[통합] 스터디 그룹 소유 체크 - 실패")
    void notStudyGroupOwn() {
        //given

        //when

        //then

    }
}