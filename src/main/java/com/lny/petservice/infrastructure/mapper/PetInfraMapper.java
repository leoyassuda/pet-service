package com.lny.petservice.infrastructure.mapper;

import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = FoodInfraMapper.class)
public interface PetInfraMapper {

    @Mapping(target = "foodEntitySet", source = "foodSet")
    @Mapping(target = "size", ignore = true)
    PetEntity toEntity(Pet pet);

    List<PetEntity> toEntity(List<Pet> petList);


    @Mapping(target = "foodSet", source = "foodEntitySet")
    Pet toDomain(PetEntity petEntity);

    List<Pet> toDomain(List<PetEntity> petEntityList);
}
