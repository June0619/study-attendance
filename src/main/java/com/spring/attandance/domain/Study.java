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

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private StudyStatus status;

    @Builder
    public Study(Member owner, LocalDateTime startTime, LocalDateTime endTime) {
        this.owner = owner;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = StudyStatus.OPEN;
    }

}
