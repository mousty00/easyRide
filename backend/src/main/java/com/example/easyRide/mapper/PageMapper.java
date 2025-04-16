package com.example.easyRide.mapper;

import com.example.easyRide.pagination.PageDto;
import com.example.easyRide.pagination.SliceDto;

import java.util.List;

public interface PageMapper<Entity, DTO> extends BaseMapper<Entity,DTO> {
    PageDto<DTO> toDtoWithPage(List<Entity> entity);

    SliceDto<DTO> toDtoWithSlice(List<Entity> entity);
}
