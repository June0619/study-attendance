package com.spring.attandance.domain;

import lombok.*;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "NAME_MOBILE_UNIQUE", columnNames = {"USER_NAME", "MOBILE"})})
public class User extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, name = "USER_NAME", length = 10)
    private String name;
    @Column(nullable = false, name = "MOBILE", length = 12)
    private String mobile;

    /** Entity Update **/
    public void update(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    /** Builder **/
    @Builder
    public User(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

}
