package com.spring.attandance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Group {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false, name = "GROUP_NAME")
    private String name;

    
}
