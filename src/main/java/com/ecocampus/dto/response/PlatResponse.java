package com.ecocampus.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlatResponse {

    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Integer quantiteDisponible;
    private String photoUrl;
    private String restaurantNom;
    private String categorieNom;
    private List<String> allergeneNoms;
}