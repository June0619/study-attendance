package com.spring.attandance.service;

import com.spring.attandance.domain.StudyGroup;
import com.spring.attandance.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudyGroupValidatorImpl implements StudyGroupValidator{

    private final StudyGroupRepository repository;

    /***
     * 스터디 그룹 생성 제한 초과 여부 Validation
     * @param memberId
     */
    @Override
    public void exceedLimitGroupCount(Long memberId) {
        long studyGroupCount = repository.countStudyGroupsByMasterId(memberId);
        if (studyGroupCount >= 3) {
            throw new IllegalStateException("스터디 그룹 생성 제한 초과");
        }
    }
}
