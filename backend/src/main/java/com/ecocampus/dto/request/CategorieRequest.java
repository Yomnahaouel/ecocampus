package com.ecocampus.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorieRequest {

    @NotBlank(message = "Le nom de la categorie est obligatoire")
    private String nom;

    private String description;
}

