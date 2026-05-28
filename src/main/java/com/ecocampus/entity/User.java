package com.ecocampus.entity;

import com.ecocampus.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

// NE PAS importer org.springframework.context.annotation.Profile
// Importer votre entité Profile :
import com.ecocampus.entity.Profile;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nom;
    private String prenom;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();
}