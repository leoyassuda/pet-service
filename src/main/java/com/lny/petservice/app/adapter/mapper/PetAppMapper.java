package com.lny.petservice.app.adapter.mapper;

import com.lny.petservice.app.adapter.dto.request.PetRequestDto;
import com.lny.petservice.app.adapter.dto.response.PetResponseDto;
import com.lny.petservice.domain.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(uses = FoodAppMapper.class)
public interface PetAppMapper {

    @Mapping(target = "foodResponseDtoSet", source = "foodSet")
    PetResponseDto toResponseDto(Pet pet);

    List<PetResponseDto> toResponseDto(List<Pet> petList);

    Set<PetResponseDto> toResponseDto(Set<Pet> petSet);

    @Mapping(target = "foodSet", source = "foodRequestDtoSet")
    Pet toDomain(PetRequestDto petRequestDto);

}
