package com.spring.attandance.controller;

import com.spring.attandance.config.aop.GroupAuth;
import com.spring.attandance.config.auth.LoginMember;
import com.spring.attandance.controller.dto.group.GroupCreateDTO;
import com.spring.attandance.controller.dto.group.GroupResponseDTO;
import com.spring.attandance.controller.dto.group.GroupUpdateDTO;
import com.spring.attandance.controller.dto.member.LoginMemberDTO;
import com.spring.attandance.domain.cond.GroupSearchCondition;
import com.spring.attandance.repository.query.GroupQueryRepository;
import com.spring.attandance.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.spring.attandance.domain.enums.GroupRole.MASTER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/group")
public class GroupController {

    private final GroupService groupService;
    private final GroupQueryRepository groupQueryRepository;

    @GetMapping
    public Page<GroupResponseDTO> list(GroupSearchCondition condition, Pageable pageable) {
        return groupQueryRepository.searchGroupList(condition, pageable);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody GroupCreateDTO dto, @LoginMember LoginMemberDTO loginMember) {
        Long createdId = groupService.create(dto, loginMember);
        return ResponseEntity.created(URI.create("api/v1/group/" + createdId)).build();
    }

//    @GroupAuth({MASTER})
    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody GroupUpdateDTO dto, @LoginMember LoginMemberDTO loginMember) {
        Long updatedId = groupService.update(id, dto, loginMember);
        return ResponseEntity.accepted().body(updatedId);
    }

    @PostMapping("/{id}/enroll")
    public ResponseEntity<Long> enroll(@PathVariable Long id, @LoginMember LoginMemberDTO loginMember) {
        groupService.enroll(id, loginMember);
        return ResponseEntity.ok(id);
    }

}
