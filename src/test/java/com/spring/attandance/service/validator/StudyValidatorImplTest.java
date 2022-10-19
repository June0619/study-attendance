package com.spring.attandance.service.validator;

import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.Study;
import com.spring.attandance.domain.StudyMember;
import com.spring.attandance.domain.enums.GroupRole;
import com.spring.attandance.repository.GroupMemberRepository;
import com.spring.attandance.repository.StudyMemberRepository;
import com.spring.attandance.repository.StudyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.spring.attandance.domain.enums.GroupRole.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class StudyValidatorImplTest {

    @Mock
    StudyRepository studyRepository;

    @Mock
    GroupMemberRepository groupMemberRepository;

    @Mock
    StudyMemberRepository studyMemberRepository;

    @InjectMocks
    StudyValidatorImpl validator;

    @Test
    @DisplayName("[단위] 스터디 그룹 관리자 여부 체크 - 성공")
    void isStudyGroupAdmin() {
        //given
        GroupMember groupMember = GroupMember.builder()
                .role(ADMIN)
                .build();

        //when
        doReturn(Optional.of(groupMember)).when(groupMemberRepository).findByMemberIdAndGroupId(1L, 1L);

        //then
        assertDoesNotThrow(() -> validator.isStudyGroupAdmin(1L, 1L));
    }

    @Test
    @DisplayName("[단위] 스터디 그룹 관리자 여부 체크 - 실패 - 어드민이 아님")
    void isStudyGroupAdmin_fail1() {
        //given
        GroupMember groupMember = GroupMember.builder()
                .role(MEMBER)
                .build();

        //when
        doReturn(Optional.of(groupMember)).when(groupMemberRepository).findByMemberIdAndGroupId(1L, 1L);

        //then
        assertThrows(IllegalStateException.class, () -> validator.isStudyGroupAdmin(1L, 1L));
    }

    @Test
    @DisplayName("[단위] 스터디 소유자 체크 - 성공")
    void isStudyOwner() throws NoSuchFieldException, IllegalAccessException {
        //given
        Member member = Member.builder()
                .build();

        Field memberId = Member.class.getDeclaredField("id");
        memberId.setAccessible(true);
        memberId.set(member, 1L);

        Study study = Study.builder()
                .owner(member)
                .build();

        //when
        doReturn(Optional.of(study)).when(studyRepository).findById(1L);

        //then
        assertDoesNotThrow(() -> validator.isStudyOwner(1L, 1L));
    }

    @Test
    @DisplayName("[단위] 스터디 소유자 체크 - 실패 - 소유자가 아님")
    void isStudyOwner_fail() throws NoSuchFieldException, IllegalAccessException {
        //given
        Member member = Member.builder()
                .build();

        Field memberId = Member.class.getDeclaredField("id");
        memberId.setAccessible(true);
        memberId.set(member, 2L);

        Study study = Study.builder()
                .owner(member)
                .build();

        //when
        doReturn(Optional.of(study)).when(studyRepository).findById(1L);

        //then
        assertThrows(IllegalStateException.class, () -> validator.isStudyOwner(1L, 1L));
    }

    @Test
    @DisplayName("[단위] 스터디 멤버 여부 체크 - 성공")
    void isStudyGroupMember() {
        //given
        GroupMember groupMember = GroupMember.builder()
                .role(MEMBER)
                .build();

        //when
        doReturn(Optional.of(groupMember)).when(groupMemberRepository).findByMemberIdAndGroupId(1L, 1L);

        //then
        assertDoesNotThrow(() -> validator.isStudyGroupMember(1L, 1L));
    }

    @Test
    @DisplayName("[단위] 스터디 멤버 여부 체크 - 실패 - 멤버가 아님")
    void isStudyGroupMember_fail1() {
        //when
        doReturn(Optional.empty()).when(groupMemberRepository).findByMemberIdAndGroupId(1L, 1L);

        //then
        assertThrows(IllegalStateException.class, () -> validator.isStudyGroupMember(1L, 1L));
    }

    @Test
    @DisplayName("[단위] 스터디 멤버 여부 체크 - 실패 - 가입 신청 대기중")
    void isStudyGroupMember_fail2() {
        //given
        GroupMember groupMember = GroupMember.builder()
                .role(WAIT)
                .build();

        //when
        doReturn(Optional.empty()).when(groupMemberRepository).findByMemberIdAndGroupId(1L, 1L);

        //then
        assertThrows(IllegalStateException.class, () -> validator.isStudyGroupMember(1L, 1L));
    }

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