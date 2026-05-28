package com.ecocampus.entity;

import com.ecocampus.entity.enums.StatutReservation;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateReservation;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    // Relation ManyToOne vers User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Relation ManyToOne vers Plat
    @ManyToOne
    @JoinColumn(name = "plat_id")
    private Plat plat;
}