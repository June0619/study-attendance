package com.spring.attandance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

}
