package com.ecocampus.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String nom;
    private String prenom;
    private String role;
    // telephone et departement seront ajoutés plus tard via une autre méthode
    private String telephone;
    private String departement;
}