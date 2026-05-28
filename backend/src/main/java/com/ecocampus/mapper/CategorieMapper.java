package com.ecocampus.mapper;

import com.ecocampus.dto.request.CategorieRequest;
import com.ecocampus.dto.response.CategorieResponse;
import com.ecocampus.entity.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategorieMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plats", ignore = true)
    Categorie toEntity(CategorieRequest request);

    CategorieResponse toResponse(Categorie categorie);
}

