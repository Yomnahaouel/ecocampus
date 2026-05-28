package com.ecocampus.dto.response;

public record UserResponse(
        Long id,
        String email,
        String nom,
        String prenom,
        String role,
        String telephone,
        String departement,
        String adresse
) {
}

