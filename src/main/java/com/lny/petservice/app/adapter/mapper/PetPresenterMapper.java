package com.lny.petservice.app.adapter.mapper;

import com.lny.petservice.app.adapter.dto.PetResponseDto;
import com.lny.petservice.domain.model.Pet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetPresenterMapper {

    PetResponseDto toDto(Pet pet);

    List<PetResponseDto> toDto(List<Pet> petList);
}
