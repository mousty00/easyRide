package com.example.easyRide.mapper;

import java.util.List;

public interface BaseMapper<Entity,DTO> {

    Entity toEntity(DTO dto);

    DTO toDto(Entity entity);

    List<Entity> toEntityList(List<DTO> dtos);

    List<DTO> toDtoList(List<Entity> entities);

}
