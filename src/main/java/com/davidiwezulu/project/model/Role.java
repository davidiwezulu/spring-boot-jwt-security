package com.davidiwezulu.project.model;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleName name;

    // Constructors, Getters, and Setters
}

