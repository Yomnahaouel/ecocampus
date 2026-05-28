package com.ecocampus.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "allergenes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Allergene {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // "gluten", "lait", "fruits secs", "oeufs", "soja", "crustacés"

    @ManyToMany(mappedBy = "allergenes")
    private List<Plat> plats = new ArrayList<>();
}