package com.spring.attandance.controller;

import com.spring.attandance.controller.dto.studyGroup.StudyGroupCreateDTO;
import com.spring.attandance.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/studyGroup")
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @GetMapping
    public String list() {
        return "studyGroup";
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody StudyGroupCreateDTO dto) {
        Long createdId = studyGroupService.create(dto);
        return ResponseEntity.created(URI.create("api/v1/studyGroup/" + createdId)).build();
    }

}
