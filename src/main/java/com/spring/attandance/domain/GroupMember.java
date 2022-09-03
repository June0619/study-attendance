package com.spring.attandance.domain;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
public class GroupMember {

    @Id @GeneratedValue
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "GROUP_ID")
    private StudyGroup studyGroup;

    private LocalDateTime joinDateTime;

}
