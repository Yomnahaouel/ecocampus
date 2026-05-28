package com.ecocampus.mapper;

import com.ecocampus.dto.request.ReservationRequest;
import com.ecocampus.dto.response.ReservationResponse;
import com.ecocampus.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    // Convertir ReservationRequest → Reservation
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateReservation", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "plat", ignore = true)
    Reservation toEntity(ReservationRequest request);

    // Convertir Reservation → ReservationResponse
    @Mapping(source = "plat.nom", target = "platNom")
    @Mapping(source = "plat.prix", target = "platPrix")
    @Mapping(source = "plat.restaurant.nom", target = "restaurantNom")
    ReservationResponse toResponse(Reservation reservation);
}