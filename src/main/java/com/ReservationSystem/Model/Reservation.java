package com.ReservationSystem.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 🔥 ESTO ES LO QUE TE FALTA

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Resource resource;
}