package com.spring.attandance.domain;

import com.spring.attandance.domain.enums.CancelYN;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public StudyMember(Member joinMember, Study study) {
        this.joinMember = joinMember;
        this.study = study;
        this.cancelYN = CancelYN.NO;
        this.enrollDateTime = LocalDateTime.now();
    }

}
