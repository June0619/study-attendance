package com.spring.attandance.domain;

import com.spring.attandance.domain.enums.StudyStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member owner;

    @OneToOne(fetch = LAZY)
    private Group group;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private StudyStatus status;

    public static Study createStudy(Member owner, Group group, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("시작시간이 종료시간보다 늦습니다.");
        }

        Study study = new Study();
        study.owner = owner;
        study.group = group;
        study.startTime = startTime;
        study.endTime = endTime;
        study.status = StudyStatus.WAIT;
        return study;
    }

    public void updateStudyInfo(LocalDateTime startTime, LocalDateTime endTime) {
        //Study Time validation
        if (startTime.isAfter(endTime)) {
            throw new IllegalStateException("스터디 시작 시간이 종료 시간보다 늦습니다.");
        }

        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void openStudy() {
        this.status = StudyStatus.OPEN;
    }

    public void closeStudy() {
        this.status = StudyStatus.CLOSE;
    }

    @Builder
    public Study(Member owner, Group group, LocalDateTime startTime, LocalDateTime endTime) {
        this.owner = owner;
        this.group = group;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = StudyStatus.WAIT;
    }

}
