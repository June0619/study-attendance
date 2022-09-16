package com.spring.attandance.controller;

import com.spring.attandance.config.auth.LoginMember;
import com.spring.attandance.controller.dto.group.GroupCreateDTO;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public String list() {
        return "studyGroup";
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody GroupCreateDTO dto, @LoginMember LoginMemberDTO loginMember) {
        Long createdId = groupService.create(dto, loginMember);
        return ResponseEntity.created(URI.create("api/v1/group/" + createdId)).build();
    }

}
