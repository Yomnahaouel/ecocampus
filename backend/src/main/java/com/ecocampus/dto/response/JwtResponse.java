package com.ecocampus.dto.response;

public record JwtResponse(
        String token,
        String type,
        Long id,
        String email,
        String nom,
        String prenom,
        String role
) {
}

