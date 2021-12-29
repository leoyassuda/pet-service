package com.lny.petservice.app.adapter.mapper;

import com.lny.petservice.app.adapter.dto.request.PetRequestDto;
import com.lny.petservice.app.adapter.dto.response.PetResponseDto;
import com.lny.petservice.domain.model.Pet;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(uses = FoodAppMapper.class)
public interface PetAppMapper {


    PetResponseDto toResponseDto(Pet pet);

    List<PetResponseDto> toResponseDto(List<Pet> petList);

    Set<PetResponseDto> toResponseDto(Set<Pet> petSet);


    Pet toDomain(PetRequestDto petRequestDto);

}
