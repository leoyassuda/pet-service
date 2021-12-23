package com.lny.petservice.mapper;

import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.dto.PetRequestDto;
import com.lny.petservice.dto.PetResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {

    Pet toEntity(PetRequestDto petRequestDto);

    PetResponseDto toDto(Pet pet);

    List<PetResponseDto> toDto(List<Pet> pets);
}
