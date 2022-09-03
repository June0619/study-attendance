package com.spring.attandance.domain;

import lombok.*;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "NAME_MOBILE_UNIQUE", columnNames = {"MEMBER_NAME", "MOBILE"})})
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false, name = "MEMBER_NAME", length = 10)
    private String name;

    @Column(nullable = true, name = "MOBILE", length = 12)
    private String mobile;

    @Column(nullable = true, name = "EMAIL", length = 50)
    private String email;

    /** Entity Update **/
    public void update(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    /** Builder **/
    @Builder
    public Member(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

}
