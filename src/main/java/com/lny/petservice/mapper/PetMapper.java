package com.lny.petservice.mapper;

import com.lny.petservice.dto.PetRequestDto;
import com.lny.petservice.dto.PetResponseDto;
import com.lny.petservice.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PetMapper {


    Pet toEntity(PetRequestDto petRequestDto);

    PetResponseDto toDto(Pet pet);
}
