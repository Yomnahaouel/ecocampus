package com.ecocampus.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // "sandwich", "plat chaud", "dessert", "boisson"

    @OneToMany(mappedBy = "categorie")
    private List<Plat> plats = new ArrayList<>();
}