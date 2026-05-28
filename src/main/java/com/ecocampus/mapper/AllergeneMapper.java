package com.ecocampus.mapper;

import com.ecocampus.entity.Allergene;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AllergeneMapper {

    AllergeneMapper INSTANCE = Mappers.getMapper(AllergeneMapper.class);
}