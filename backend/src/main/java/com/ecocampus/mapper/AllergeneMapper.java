package com.ecocampus.mapper;

import com.ecocampus.dto.request.AllergeneRequest;
import com.ecocampus.dto.response.AllergeneResponse;
import com.ecocampus.entity.Allergene;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllergeneMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plats", ignore = true)
    Allergene toEntity(AllergeneRequest request);

    AllergeneResponse toResponse(Allergene allergene);
}
