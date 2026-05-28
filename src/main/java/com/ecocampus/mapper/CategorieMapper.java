package com.ecocampus.mapper;

import com.ecocampus.entity.Categorie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategorieMapper {

    CategorieMapper INSTANCE = Mappers.getMapper(CategorieMapper.class);
}