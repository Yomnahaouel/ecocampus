package com.ecocampus.mapper;

import com.ecocampus.dto.request.RestaurantRequest;
import com.ecocampus.dto.response.RestaurantResponse;
import com.ecocampus.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plats", ignore = true)
    Restaurant toEntity(RestaurantRequest request);

    RestaurantResponse toResponse(Restaurant restaurant);
}

