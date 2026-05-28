package com.ecocampus.mapper;

import com.ecocampus.dto.response.ReservationResponse;
import com.ecocampus.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "status", expression = "java(reservation.getStatus().name())")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "platId", source = "plat.id")
    @Mapping(target = "platNom", source = "plat.nom")
    @Mapping(target = "platPrix", source = "plat.prix")
    @Mapping(target = "restaurantNom", source = "plat.restaurant.nom")
    ReservationResponse toResponse(Reservation reservation);
}
