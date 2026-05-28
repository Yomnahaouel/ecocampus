package com.ecocampus.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantRequest {

    @NotBlank(message = "Le nom du restaurant est obligatoire")
    private String nom;

    private String adresse;
    private String telephone;
}

