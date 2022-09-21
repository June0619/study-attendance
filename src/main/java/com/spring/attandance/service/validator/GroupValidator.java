package com.spring.attandance.service.validator;

public interface GroupValidator {
    void exceedLimitGroupCount(Long memberId);
    void isGroupMaster(Long memberId, Long groupId);
    void isGroupMember(Long memberId, Long groupId);
}
