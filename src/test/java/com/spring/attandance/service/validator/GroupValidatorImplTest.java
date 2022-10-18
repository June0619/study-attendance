package com.spring.attandance.service.validator;

import com.spring.attandance.domain.Group;
import com.spring.attandance.domain.GroupMember;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.enums.GroupRole;
import com.spring.attandance.repository.GroupMemberRepository;
import com.spring.attandance.repository.GroupRepository;
import com.spring.attandance.service.validator.GroupValidatorImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class GroupValidatorImplTest {

    @Mock
    GroupRepository repository;

    @Mock
    GroupMemberRepository groupMemberRepository;

    @InjectMocks
    GroupValidatorImpl validator;

    @Test
    @DisplayName("[단위] 스터디 그룹 생성 범위 초과 테스트 - 성공")
    void exceed() {
        //given
        Long memberId = 1L;

        //when
        doReturn(2L).when(groupMemberRepository).countGroupsByMemberId(memberId);

        //then
        assertDoesNotThrow(() -> validator.exceedLimitGroupCount(memberId));
    }

    @Test
    @DisplayName("[단위] 스터디 그룹 생성 범위 초과 테스트 - 실패")
    void exceed_fail() {
        //given
        Long memberId = 1L;

        //when
        doReturn(3L).when(groupMemberRepository).countGroupsByMemberId(memberId);

        //then
        assertThrows(IllegalStateException.class, () -> validator.exceedLimitGroupCount(memberId));
    }

    @Test
    @DisplayName("[단위] 스터디 그룹 수정 권한 테스트 - 성공")
    void isGroupMaster() throws Exception {
        //given
        Long memberId = 1L;
        Long groupId = 1L;

        Member member = Member.builder()
                .build();

        Group group = Group.builder()
                .build();

        Class<Member> memberClass = Member.class;

        Field memberIdField = Member.class.getDeclaredField("id");
        memberIdField.setAccessible(true);
        memberIdField.set(member, memberId);

        Field groupIdField = Group.class.getDeclaredField("id");
        groupIdField.setAccessible(true);
        groupIdField.set(group, groupId);

        Optional<GroupMember> result = Optional.of(GroupMember.builder()
                .member(member)
                .group(group)
                .role(GroupRole.MASTER)
                .build());

        //when
        doReturn(result).when(groupMemberRepository).findByMemberIdAndGroupId(memberId, groupId);

        //then
        assertDoesNotThrow(() -> validator.isGroupMaster(memberId, groupId));
    }

    @Test
    @DisplayName("[단위] 스터디 그룹 수정 권한 테스트 - 실패")
    void isGroupMaster_fail() {

        //given
        Member m1 = Member.builder()
                .name("test")
                .build();

        Group g1 = Group.builder()
                .build();

        Optional<GroupMember> result1 = Optional.of(GroupMember.builder()
                .member(m1)
                .group(g1)
                .role(GroupRole.MEMBER)
                .build());

        Optional<Object> result2 = Optional.empty();


        //when
        doReturn(result1).when(groupMemberRepository).findByMemberIdAndGroupId(1L, 1L);
        doReturn(result2).when(groupMemberRepository).findByMemberIdAndGroupId(2L, 2L);

        //then
        assertThrows(IllegalStateException.class, () -> validator.isGroupMaster(1L, 1L));
        assertThrows(IllegalStateException.class, () -> validator.isGroupMaster(2L, 2L));
    }
}