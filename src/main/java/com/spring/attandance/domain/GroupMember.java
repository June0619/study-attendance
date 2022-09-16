package com.spring.attandance.domain;

import com.spring.attandance.domain.enums.GroupRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @Enumerated(EnumType.STRING)
    private GroupRole role;

    @Builder
    public GroupMember(Member member, Group group, GroupRole role) {
        this.member = member;
        this.group = group;
        this.role = role;
    }
}
