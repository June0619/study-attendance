package com.spring.attandance.domain;

import com.spring.attandance.controller.dto.member.MemberUpdateDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "NAME_MOBILE_UNIQUE", columnNames = {"MEMBER_NAME", "MOBILE"})})
@ToString(of = {"id", "name", "mobile", "email"})
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false, name = "MEMBER_NAME", length = 10)
    private String name;

    @Column(nullable = true, name = "MOBILE", length = 12)
    private String mobile;

    @Column(nullable = true, name = "EMAIL", length = 50)
    private String email;

    @OneToMany(mappedBy = "member")
    private List<GroupMember> groupMembers = new ArrayList<>();

    /** Entity Update **/
    public void update(String name, String email) {
        if(name != null) this.name = name;
        if(email != null) this.email = email;
    }

    public void update(MemberUpdateDTO dto) {
        this.update(dto.getName(), dto.getEmail());
    }

    /** Builder **/
    @Builder
    public Member(String name, String mobile, String email) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
    }

}
