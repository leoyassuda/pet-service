package com.lny.petservice.infrastructure.mapper;

import com.lny.petservice.domain.model.Food;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.FoodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(uses = PetInfraMapper.class)
public interface FoodInfraMapper {
    
    @Mapping(target = "petEntity", ignore = true)
    FoodEntity toEntity(Food food);

    Set<FoodEntity> toEntity(Set<Food> foodSet);

    @Mapping(target = "pet", ignore = true)
    Food toDomain(FoodEntity foodEntity);

    Set<Food> toDomain(Set<FoodEntity> foodEntitySet);
}
