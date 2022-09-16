package com.spring.attandance.service.validator;

import com.spring.attandance.domain.enums.GroupRole;
import com.spring.attandance.repository.GroupMemberRepository;
import com.spring.attandance.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupValidatorImpl implements GroupValidator {

    private final GroupRepository repository;
    private final GroupMemberRepository groupMemberRepository;

    /***
     * 스터디 그룹 생성 제한 초과 여부 Validation
     * @param memberId
     */
    @Override
    public void exceedLimitGroupCount(Long memberId) {
        long groupCount = repository.countGroupsByMasterId(memberId);
        if (groupCount >= 3) {
            throw new IllegalStateException("스터디 그룹 생성 제한 초과");
        }
    }

    /**
     * 스터디 그룹 수정 권한 Validation
     * @param memberId
     * */
    @Override
    public void isGroupMaster(Long memberId, Long groupId) {

        boolean isGroupMaster = groupMemberRepository.findByMemberIdAndGroupId(memberId, groupId)
                .map(groupMember -> groupMember.getRole().equals(GroupRole.MASTER))
                .orElse(false);

        if (!isGroupMaster) {
            throw new IllegalStateException("스터디 그룹 수정 권한이 없습니다.");
        }
    }

}
