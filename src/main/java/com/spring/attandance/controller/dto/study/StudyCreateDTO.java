package com.spring.attandance.controller.dto.study;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudyCreateDTO {

    private Long groupId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
