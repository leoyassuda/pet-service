package com.lny.petservice.app.adapter.mapper;

import com.lny.petservice.app.adapter.dto.request.FoodRequestDto;
import com.lny.petservice.app.adapter.dto.response.FoodResponseDto;
import com.lny.petservice.domain.model.Food;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(uses = PetAppMapper.class)
public interface FoodAppMapper {


    FoodResponseDto toResponseDto(Food food);

    Set<FoodResponseDto> toResponseDto(Set<Food> foodSet);


    Food toDomain(FoodRequestDto foodRequestDto);

    Set<Food> toDomain(Set<FoodRequestDto> foodRequestDtoSet);
}
