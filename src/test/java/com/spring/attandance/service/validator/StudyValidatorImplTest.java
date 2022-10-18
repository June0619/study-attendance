package com.spring.attandance.service.validator;

import com.spring.attandance.domain.Study;
import com.spring.attandance.domain.StudyMember;
import com.spring.attandance.repository.StudyMemberRepository;
import com.spring.attandance.repository.StudyRepository;
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
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class StudyValidatorImplTest {

    @Mock
    StudyRepository studyRepository;

    @Mock
    StudyMemberRepository studyMemberRepository;

    @InjectMocks
    StudyValidatorImpl validator;

    @Test
    @DisplayName("[단위] 스터디 중복 참여 체크 - 성공")
    void periodCheck() {
        //given
        //참여중인 스터디 - 두 시간 전에 시작해서 한 시간 전에 끝난다.
        Study s1 = Study.builder()
                .startTime(LocalDateTime.now().minusHours(2))
                .endTime(LocalDateTime.now().minusHours(1))
                .build();

        StudyMember studyMember = StudyMember.builder()
                .study(s1)
                .build();

        //참여하려는 스터디 - 현재 부터 시작해서 한시간 후에 끝난다.
        Study s2 = Study.builder()
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .build();

        //when
        //스터디 조회 Mocking
        doReturn(Optional.of(s2)).when(studyRepository).findById(2L);
        //스터디-회원 관계형 테이블 Mocking
        doReturn(List.of(studyMember)).when(studyMemberRepository).findByJoinMemberId(1L);

        //then
        //참여하려는 스터디와 참여중인 스터디가 겹치지 않으므로 예외가 발생하지 않는다.
        assertDoesNotThrow(() -> validator.periodCheck(1L, 2L));
    }

    @Test
    @DisplayName("[단위] 스터디 중복 참여 체크 - 실패1 - 시작 시간 겹침")
    void periodCheck_fail1() {
        //given
        //참여중인 스터디 - 한 시간 전에 시작해서 한 시간 후에 끝난다.
        Study s1 = Study.builder()
                .startTime(LocalDateTime.now().minusHours(1))
                .endTime(LocalDateTime.now().plusHours(1))
                .build();

        StudyMember studyMember = StudyMember.builder()
                .study(s1)
                .build();

        //참여하려는 스터디 - 현재 부터 시작해서 두 시간 후에 끝난다.
        Study s2 = Study.builder()
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(2))
                .build();

        //when
        //스터디 조회 Mocking
        doReturn(Optional.of(s2)).when(studyRepository).findById(2L);
        //스터디-회원 관계형 테이블 Mocking
        doReturn(List.of(studyMember)).when(studyMemberRepository).findByJoinMemberId(1L);


        //then
        //참여하려는 스터디와 참여중인 스터디가 겹치므로 예외가 발생한다.
        assertThrows(IllegalStateException.class, () -> validator.periodCheck(1L, 2L));
    }

}