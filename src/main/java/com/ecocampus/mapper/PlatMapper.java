package com.ecocampus.mapper;

import com.ecocampus.dto.request.PlatRequest;
import com.ecocampus.dto.response.PlatResponse;
import com.ecocampus.entity.Plat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PlatMapper {

    PlatMapper INSTANCE = Mappers.getMapper(PlatMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "categorie", ignore = true)
    @Mapping(target = "allergenes", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Plat toEntity(PlatRequest request);

    @Mapping(source = "restaurant.nom", target = "restaurantNom")
    @Mapping(source = "categorie.nom", target = "categorieNom")
    @Mapping(target = "allergeneNoms", expression = "java(getAllergeneNames(plat.getAllergenes()))")
    PlatResponse toResponse(Plat plat);

    // Méthode par défaut pour convertir les allergènes en noms
    default List<String> getAllergeneNames(List<com.ecocampus.entity.Allergene> allergenes) {
        if (allergenes == null) {
            return new ArrayList<>();
        }
        List<String> names = new ArrayList<>();
        for (com.ecocampus.entity.Allergene a : allergenes) {
            if (a != null && a.getNom() != null) {
                names.add(a.getNom());
            }
        }
        return names;
    }
}