package com.spring.attandance.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class StudyGroup {

    @Id @GeneratedValue
    @Column(name = "GROUP_ID")
    private long id;

    @Column(nullable = false, name = "GROUP_NAME")
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member master;



    
}
