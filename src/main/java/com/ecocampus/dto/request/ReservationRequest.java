package com.ecocampus.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull(message = "L'ID du plat est obligatoire")
    private Long platId;
}