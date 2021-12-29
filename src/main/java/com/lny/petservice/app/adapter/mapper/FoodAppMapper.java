package com.lny.petservice.app.adapter.mapper;

import com.lny.petservice.app.adapter.dto.request.FoodRequestDto;
import com.lny.petservice.app.adapter.dto.response.FoodResponseDto;
import com.lny.petservice.domain.model.Food;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper
public interface FoodAppMapper {

    @Mapping(target = "petResponseDto", ignore = true)
    FoodResponseDto toResponseDto(Food food);

    Set<FoodResponseDto> toResponseDto(Set<Food> foodSet);

    @Mapping(target = "pet", ignore = true)
    Food toDomain(FoodRequestDto foodRequestDto);

    Set<Food> toDomain(Set<FoodRequestDto> foodRequestDtoSet);
}
