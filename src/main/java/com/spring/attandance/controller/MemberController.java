package com.spring.attandance.controller;

import com.spring.attandance.controller.dto.member.MemberCreateDTO;
import com.spring.attandance.controller.dto.member.MemberUpdateDTO;
import com.spring.attandance.domain.Member;
import com.spring.attandance.domain.cond.MemberSearchCondition;
import com.spring.attandance.repository.query.MemberQueryRepository;
import com.spring.attandance.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryRepository memberQueryRepository;

    @GetMapping
    public Page<Member> list(MemberSearchCondition condition, Pageable pageable) {
        return memberQueryRepository.searchMemberList(condition, pageable);
    }

    @PostMapping
    public ResponseEntity<Long> join(@RequestBody MemberCreateDTO dto) {
        Long joinedId = memberService.join(dto::toEntity);
        return ResponseEntity.created(URI.create("/api/v1/member/" + joinedId)).build();
    }

    @PutMapping
    public ResponseEntity<Long> update(@RequestBody MemberUpdateDTO dto) {
        memberService.updateMember(dto);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.accepted().build();
    }
}
