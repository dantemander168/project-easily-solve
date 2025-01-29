package com.learn.EasilySolveApp.pojos.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "USER_TABLE", schema = "easily_solve_owner")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @NotBlank(message = "First Name is required")
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "USER_TYPE", nullable = false)
    private String userType;

}
