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

        doReturn(Optional.empty()).when(repository).findOne(cond);

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

        doReturn(Optional.of(givenMember)).when(repository).findOne(cond);

        //when
        Member member = Member.builder()
                .mobile("01012345678")
                .build();

        //then
        assertThrows(IllegalStateException.class, () -> validator.duplicateCheck(member));
    }

}