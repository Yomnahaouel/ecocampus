package com.ecocampus.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record PlatResponse(
        Long id,
        String nom,
        String description,
        BigDecimal prix,
        Integer quantite,
        String photoUrl,
        boolean disponible,
        Long restaurantId,
        String restaurantNom,
        Long categorieId,
        String categorieNom,
        List<String> allergenes
) {
}
