package com.spring.attandance.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class StudyGroup {

    @Id @GeneratedValue
    @Column(name = "GROUP_ID")
    private long id;

    @Column(nullable = false, name = "GROUP_NAME")
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member master;

    @Builder
    public StudyGroup(String name, Member master) {
        this.name = name;
        this.master = master;
    }

    
}
