package com.spring.attandance.controller.dto.group;

import com.spring.attandance.domain.Group;
import lombok.Data;

@Data
public class StudyGroupCreateDTO {

    private String name;

    public Group toEntity() {
        return Group.builder()
                .name(this.name)
                .build();
    }
}
