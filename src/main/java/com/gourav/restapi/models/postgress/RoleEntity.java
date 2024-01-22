package com.gourav.restapi.models.postgress;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERole name;

    public RoleEntity(ERole name) {
        this.name = name;
    }
}
