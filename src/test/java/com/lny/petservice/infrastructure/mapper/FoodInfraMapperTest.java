package com.lny.petservice.infrastructure.mapper;

import com.lny.petservice.domain.model.Food;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.FoodEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Test Food mapper in infrastructure objects")
@IndicativeSentencesGeneration(separator = " -> ")
class FoodInfraMapperTest {

    private final FoodInfraMapper foodInfraMapper = Mappers.getMapper(FoodInfraMapper.class);

    @Test
    @DisplayName("Return equals values between Food Domain and Food Entity using MapStruct mapper")
    void testDomainPetObjectToEntityObject() {

        Food food = this.buildFoodDomain();

        FoodEntity foodEntity = foodInfraMapper.toEntity(food);

        assertEquals(food.getId(), foodEntity.getId());
        assertEquals(food.getName(), foodEntity.getName());

        assertNull(foodEntity.getPetEntity());

    }

    @Test
    @DisplayName("Return a converted entity list using Pet Domain list")
    void testPetDomainListToEntityList() {
        Set<Food> foodSet = new HashSet<>();
        foodSet.add(this.buildFoodDomain());
        foodSet.add(this.buildFoodDomain());

        Set<FoodEntity> foodEntitySet = foodInfraMapper.toEntity(foodSet);

        assertEquals(2, foodEntitySet.size());

        Food food1 = foodSet.stream().findAny().orElse(new Food());
        assertTrue(foodEntitySet.stream().anyMatch(foodEntity -> foodEntity.getId().equals(food1.getId())),
                "Fail to find same UUID between domain list and entity list items");

    }

    @Test
    @DisplayName("Return equals values between Food Entity and Food Domain using MapStruct mapper")
    void testEntityFoodObjectToDomainObject() {

        FoodEntity foodEntity = this.buildFoodEntity();

        Food foodDomain =foodInfraMapper.toDomain(foodEntity);

        assertEquals(foodEntity.getId(), foodDomain.getId());
        assertEquals(foodEntity.getName(), foodDomain.getName());

        assertNull(foodDomain.getPet());

    }

    @Test
    @DisplayName("Return a converted domain list using Food Entity list")
    void testPetEntityListToDomainList() {
        Set<FoodEntity> foodEntitySet = new HashSet<>();
        foodEntitySet.add(this.buildFoodEntity());
        foodEntitySet.add(this.buildFoodEntity());

        Set<Food> foodSet = foodInfraMapper.toDomain(foodEntitySet);

        assertEquals(2, foodEntitySet.size());

        FoodEntity foodEntity = foodEntitySet.stream().findAny().orElse(new FoodEntity());
        assertTrue(foodSet.stream().anyMatch(food -> food.getId().equals(foodEntity.getId())),
                "Fail to find same UUID between entity list and domain list items");

    }

    private Food buildFoodDomain() {
        return Food.builder()
                .id(UUID.randomUUID())
                .name("Snack " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .build();
    }

    private FoodEntity buildFoodEntity(){
        return FoodEntity.builder()
                .id(UUID.randomUUID())
                .name("Snack " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .build();
    }
}
