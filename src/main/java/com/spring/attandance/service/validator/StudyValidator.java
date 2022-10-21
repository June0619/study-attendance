package com.spring.attandance.service.validator;

import com.spring.attandance.domain.Study;

public interface StudyValidator {

    void isStudyGroupAdmin(Long memberId, Long studyId);
    void isStudyOwner(Long memberId, Long studyId);
    void isStudyGroupMember(Long memberId, Long studyId);
    void periodCheck(Long memberId, Study study);

    default void hasStudyUpdatePermission(Long memberId, Long studyId, Long groupId) {
        try {
            isStudyOwner(memberId, studyId);
        } catch (IllegalStateException e) {
            isStudyGroupAdmin(memberId, groupId);
        }
    }
}
