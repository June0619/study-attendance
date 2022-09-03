package com.spring.attandance.domain;

import com.spring.attandance.domain.enums.CancelYN;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
public class StudyMember {

    @Id @GeneratedValue
    @Column(name = "STUDY_MEMBER_ID")
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member joinMember;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "STUDY_ID")
    private Study study;

    @Enumerated(EnumType.STRING)
    private CancelYN cancelYN;

    private LocalDateTime enrollDateTime;

}
