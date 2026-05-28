package com.ecocampus.dto.response;

public record RestaurantResponse(
        Long id,
        String nom,
        String adresse,
        String telephone
) {
}

