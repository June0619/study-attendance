package com.spring.attandance.domain.cond;

import com.spring.attandance.domain.enums.PassedStudy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class StudySearchCondition {

    private String name;
    private Long ownerId;
    private PassedStudy passedStudy;
}
