package com.gourav.restapi.models.postgress;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @Size(max = 120)
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    private RoleEntity role;

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
