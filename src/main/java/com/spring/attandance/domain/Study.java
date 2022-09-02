package com.spring.attandance.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
public class Study {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Member owner;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
