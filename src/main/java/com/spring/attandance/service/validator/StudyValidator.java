package com.spring.attandance.service.validator;

public interface StudyValidator {

    void isStudyAdmin(Long memberId, Long studyId);
    void isStudyMember(Long memberId, Long studyId);
    void isHaveOngoingStudy(Long memberId);

}
