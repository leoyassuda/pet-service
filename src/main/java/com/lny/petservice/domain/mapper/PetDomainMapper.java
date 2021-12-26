package com.lny.petservice.domain.mapper;

import com.lny.petservice.app.adapter.dto.PetRequestDto;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetDomainMapper {

    Pet toDomain(PetRequestDto petRequestDto);

    Pet toDomain(PetEntity petEntity);

    List<Pet> toDomain(List<PetEntity> petEntities);

}
