package com.spring.attandance.controller.dto.study;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudyUpdateDTO {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
