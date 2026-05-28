package com.ecocampus.mapper;

import com.ecocampus.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    // Pour les cas simples, on expose juste l'INSTANCE
    // Les méthodes seront ajoutées au besoin
}