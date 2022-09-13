package com.spring.attandance.domain;

import com.spring.attandance.domain.enums.GroupRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "GROUP_ID")
    private StudyGroup studyGroup;

    @Enumerated(EnumType.STRING)
    private GroupRole role;

    @Builder
    public GroupMember(Member member, StudyGroup studyGroup, GroupRole role) {
        this.member = member;
        this.studyGroup = studyGroup;
        this.role = role;
    }
}
