package com.ecocampus.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlatRequest {

    @NotBlank(message = "Le nom du plat est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être positif")
    private Double prix;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 0, message = "La quantité ne peut pas être négative")
    private Integer quantiteDisponible;

    private String photoUrl;

    @NotNull(message = "L'ID du restaurant est obligatoire")
    private Long restaurantId;

    @NotNull(message = "L'ID de la catégorie est obligatoire")
    private Long categorieId;

    private List<Long> allergeneIds;
}