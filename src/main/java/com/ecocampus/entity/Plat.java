package com.ecocampus.entity;

import com.ecocampus.entity.enums.StatutReservation;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private Double prix;
    private Integer quantiteDisponible;
    private String photoUrl;

    // Relation ManyToOne vers Restaurant
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    // Relation ManyToOne vers Categorie
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    // Relation ManyToMany vers Allergene
    @ManyToMany
    @JoinTable(
            name = "plat_allergene",
            joinColumns = @JoinColumn(name = "plat_id"),
            inverseJoinColumns = @JoinColumn(name = "allergene_id")
    )
    private List<Allergene> allergenes = new ArrayList<>();

    // Relation OneToMany vers Reservation
    @OneToMany(mappedBy = "plat")
    private List<Reservation> reservations = new ArrayList<>();
}