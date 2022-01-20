package com.example.websocketexample.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@ToString(callSuper = true, doNotUseGetters = true)
@Entity(name = "role")
@Table(name = "role")
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", unique = true, nullable = false, length = 20)
    private ERole role;

    /* RELATIONS */

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<User> users;


}
