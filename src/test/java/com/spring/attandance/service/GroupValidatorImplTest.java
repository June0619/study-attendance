package com.spring.attandance.service;

import com.spring.attandance.repository.GroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GroupValidatorImplTest {

    @Mock
    GroupRepository repository;

    @InjectMocks
    GroupValidatorImpl validator;

    @Test
    @DisplayName("[단위] 스터디 그룹 생성 범위 초과 테스트 - 성공")
    void exceed() {
        //given
        Long memberId = 1L;

        //when
        doReturn(2).when(repository).countGroupsByMasterId(memberId);

        //then
        assertDoesNotThrow(() -> validator.exceedLimitGroupCount(memberId));
    }

    @Test
    @DisplayName("[단위] 스터디 그룹 생성 범위 초과 테스트 - 실패")
    void exceed_fail() {
        //given
        Long memberId = 1L;

        //when
        doReturn(3).when(repository).countGroupsByMasterId(memberId);

        //then
        assertThrows(IllegalStateException.class, () -> validator.exceedLimitGroupCount(memberId));
    }
}