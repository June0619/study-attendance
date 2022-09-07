package com.spring.attandance.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member owner;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public Study(Member owner, LocalDateTime startTime, LocalDateTime endTime) {
        this.owner = owner;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
