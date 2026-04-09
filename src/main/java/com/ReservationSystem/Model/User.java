package com.ReservationSystem.Model;

import jakarta.persistence.*; // ✅ ESTE ES EL IMPORT CORRECTO
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id // ✅ AHORA SÍ FUNCIONA
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}