package com.ecocampus.mapper;

import com.ecocampus.dto.response.PlatResponse;
import com.ecocampus.entity.Plat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlatMapper {

    @Mapping(target = "restaurantId", source = "restaurant.id")
    @Mapping(target = "restaurantNom", source = "restaurant.nom")
    @Mapping(target = "categorieId", source = "categorie.id")
    @Mapping(target = "categorieNom", source = "categorie.nom")
    @Mapping(target = "allergenes", expression = "java(plat.getAllergenes().stream().map(com.ecocampus.entity.Allergene::getNom).toList())")
    PlatResponse toResponse(Plat plat);
}
