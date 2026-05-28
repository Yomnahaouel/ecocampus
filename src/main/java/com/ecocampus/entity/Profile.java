package com.ecocampus.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telephone;
    private String departement;
    private String adresse;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // Constructeur par défaut
    public Profile() {}

    // Constructeur avec tous les champs
    public Profile(Long id, String telephone, String departement, String adresse, User user) {
        this.id = id;
        this.telephone = telephone;
        this.departement = departement;
        this.adresse = adresse;
        this.user = user;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getDepartement() {
        return departement;
    }

    public String getAdresse() {
        return adresse;
    }

    public User getUser() {
        return user;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setUser(User user) {
        this.user = user;
    }
}