package com.ecocampus.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllergeneRequest {

    @NotBlank(message = "Le nom de l'allergene est obligatoire")
    private String nom;

    private String description;
}
