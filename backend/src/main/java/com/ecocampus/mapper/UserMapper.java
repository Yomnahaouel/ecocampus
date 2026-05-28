package com.ecocampus.mapper;

import com.ecocampus.dto.request.RegisterRequest;
import com.ecocampus.dto.response.UserResponse;
import com.ecocampus.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    User toEntity(RegisterRequest request);

    @Mapping(target = "role", expression = "java(user.getRole().name())")
    @Mapping(target = "telephone", source = "profile.telephone")
    @Mapping(target = "departement", source = "profile.departement")
    @Mapping(target = "adresse", source = "profile.adresse")
    UserResponse toResponse(User user);
}

