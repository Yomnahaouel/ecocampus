package com.ecocampus.mapper;

import com.ecocampus.dto.request.RegisterRequest;
import com.ecocampus.dto.response.UserResponse;
import com.ecocampus.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Convertir RegisterRequest → User
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(RegisterRequest request);

    // Convertir User → UserResponse
    // On ignore les champs du profil pour l'instant
    @Mapping(target = "telephone", ignore = true)
    @Mapping(target = "departement", ignore = true)
    UserResponse toResponse(User user);
}