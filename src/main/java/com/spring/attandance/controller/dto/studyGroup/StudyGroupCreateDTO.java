package com.spring.attandance.controller.dto.studyGroup;

import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.StudyGroup;
import lombok.Data;

@Data
public class StudyGroupCreateDTO {

    private String name;

    public StudyGroup toEntity() {
        return StudyGroup.builder()
                .name(this.name)
                .build();
    }
}
