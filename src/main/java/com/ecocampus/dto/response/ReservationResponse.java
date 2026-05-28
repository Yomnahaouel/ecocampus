package com.ecocampus.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private Long id;
    private LocalDateTime dateReservation;
    private String statut;
    private String platNom;
    private Double platPrix;
    private String restaurantNom;
}