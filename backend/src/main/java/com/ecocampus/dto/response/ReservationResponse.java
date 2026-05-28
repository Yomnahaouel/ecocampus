package com.ecocampus.dto.response;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public record ReservationResponse(
        Long id,
        LocalDateTime dateReservation,
        String status,
        Long userId,
        String userEmail,
        Long platId,
        String platNom,
        BigDecimal platPrix,
        String restaurantNom
) {
}
