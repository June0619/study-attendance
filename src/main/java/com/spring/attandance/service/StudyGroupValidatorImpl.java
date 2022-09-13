package com.spring.attandance.service;

import com.spring.attandance.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudyGroupValidatorImpl implements StudyGroupValidator{

    private final GroupRepository repository;

    /***
     * 스터디 그룹 생성 제한 초과 여부 Validation
     * @param memberId
     */
    @Override
    public void exceedLimitGroupCount(Long memberId) {
        long studyGroupCount = repository.countGroupsByMasterId(memberId);
        if (studyGroupCount >= 3) {
            throw new IllegalStateException("스터디 그룹 생성 제한 초과");
        }
    }
}
