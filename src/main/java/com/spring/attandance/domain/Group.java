package com.spring.attandance.domain;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "STUDY_GROUP")
@ToString(of = {"id", "name"})
public class Group {

    @Id @GeneratedValue
    @Column(name = "GROUP_ID")
    private long id;

    @Column(nullable = false, name = "GROUP_NAME")
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member master;

    @OneToMany(mappedBy = "group")
    private List<GroupMember> groupMembers = new ArrayList<>();

    @Builder
    public Group(String name, Member master) {
        this.name = name;
        this.master = master;
    }

    public void update(String name) {
        this.name = name;
    }

    public void changeMaster(Member master) {
        this.master = master;
    }

    
}
