package com.lny.petservice.infrastructure.mapper;

import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(uses = FoodInfraMapper.class)
public interface PetInfraMapper {

    @Mapping(target = "foodEntitySet", source = "foodSet")
    @Mapping(target = "size", ignore = true)
    PetEntity toEntity(Pet pet);

    List<PetEntity> toEntity(List<Pet> petList);

    @Named("defaultPet")
    @Mapping(target = "foodSet", source = "foodEntitySet")
    Pet toDomain(PetEntity petEntity);

    @Named("skipFoodPet")
    @Mapping(target = "foodSet", ignore = true)
    Pet toDomainSkipFood(PetEntity petEntity);

    @IterableMapping(qualifiedByName = "defaultPet")
    List<Pet> toDomain(List<PetEntity> petEntityList);

    @IterableMapping(qualifiedByName = "skipFoodPet")
    List<Pet> toDomainSkipFood(List<PetEntity> petEntityList);
}
