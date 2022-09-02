package com.spring.attandance.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Study {

    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private Member member;

    private LocalDate openDate;
    private String startFrom;
    private String endTo;


}
