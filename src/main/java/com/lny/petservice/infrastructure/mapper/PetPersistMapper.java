package com.lny.petservice.infrastructure.mapper;

import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetPersistMapper {

    PetEntity toEntity(Pet pet);
}
