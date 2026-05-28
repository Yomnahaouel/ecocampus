package com.ecocampus.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatRequest {

    @NotBlank(message = "Le nom du plat est obligatoire")
    private String nom;

    private String description;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit etre positif")
    private BigDecimal prix;

    @NotNull(message = "La quantite est obligatoire")
    @Min(value = 0, message = "La quantite doit etre positive")
    private Integer quantite;

    private String photoUrl;

    private boolean disponible = true;

    @NotNull(message = "Le restaurant est obligatoire")
    private Long restaurantId;

    @NotNull(message = "La categorie est obligatoire")
    private Long categorieId;

    private List<Long> allergeneIds = new ArrayList<>();
}
