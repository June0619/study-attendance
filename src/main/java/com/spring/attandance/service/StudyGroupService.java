package com.spring.attandance.service;

import com.spring.attandance.domain.StudyGroup;
import com.spring.attandance.repository.StudyGroupRepository;
import com.spring.attandance.repository.query.StudyGroupQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudyGroupService {

    private final StudyGroupRepository repository;
    private final StudyGroupQueryRepository queryRepository;
    private final StudyGroupValidator validator;

    public Long create(StudyGroup studyGroup) {
        return null;
    }

    public void update() {

    }

    public void enroll() {

    }

    public void resign() {

    }

    public void delete() {

    }

}

