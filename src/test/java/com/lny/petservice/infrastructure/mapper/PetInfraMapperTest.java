package com.lny.petservice.infrastructure.mapper;

import com.lny.petservice.domain.model.Food;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.FoodEntity;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Test Pet mapper in infrastructure objects")
@IndicativeSentencesGeneration(separator = " -> ")
class PetInfraMapperTest {

    @Spy
    private FoodInfraMapper foodInfraMapper = Mappers.getMapper(FoodInfraMapper.class);

    @InjectMocks
    private PetInfraMapper petInfraMapper = Mappers.getMapper(PetInfraMapper.class);

    @Test
    @DisplayName("Return equals values between Pet Domain and Pet Entity using MapStruct mapper")
    void testDomainPetObjectToEntityObject() {

        Pet pet = this.buildPetDomain();

        PetEntity petEntity = petInfraMapper.toEntity(pet);

        assertEquals(pet.getId(), petEntity.getId());
        assertEquals(pet.getName(), petEntity.getName());
        assertEquals(pet.getBreed(), petEntity.getBreed());
        assertEquals(pet.getFoodSet().size(), petEntity.getFoodEntitySet().size());

        Food food = pet.getFoodSet().stream().findFirst().orElse(new Food());
        FoodEntity foodEntity = petEntity.getFoodEntitySet().stream().findFirst().orElse(new FoodEntity());

        assertEquals(food.getId(), foodEntity.getId());
        assertEquals(food.getName(), foodEntity.getName());
        assertNull(foodEntity.getPetEntity());

    }

    @Test
    @DisplayName("Return a converted entity list using Pet Domain list")
    void testPetDomainListToEntityList() {
        List<Pet> petList = Arrays.asList(this.buildPetDomain(), this.buildPetDomain());

        List<PetEntity> petEntityList = petInfraMapper.toEntity(petList);

        assertEquals(2, petEntityList.size());

        Pet petA = petList.stream().findAny().orElse(new Pet());
        assertTrue(petEntityList.stream().anyMatch(petEntity -> petEntity.getId().equals(petA.getId())),
                "Fail to find same UUID between domain list and entity list items");

    }

    @Test
    @DisplayName("Return equals values between Pet Entity and Pet Domain using MapStruct mapper")
    void testEntityPetObjectToDomainObject() {

        PetEntity petEntity = this.buildPetEntity();

        Pet petDomain = petInfraMapper.toDomain(petEntity);

        assertEquals(petEntity.getId(), petDomain.getId());
        assertEquals(petEntity.getName(), petDomain.getName());
        assertEquals(petEntity.getBreed(), petDomain.getBreed());
        assertEquals(petEntity.getFoodEntitySet().size(), petDomain.getFoodSet().size());

        FoodEntity foodEntity = petEntity.getFoodEntitySet().stream().findFirst().orElse(new FoodEntity());
        Food food = petDomain.getFoodSet().stream().findFirst().orElse(new Food());

        assertEquals(foodEntity.getId(), food.getId());
        assertEquals(foodEntity.getName(), food.getName());
        assertNull(food.getPet());

    }

    @Test
    @DisplayName("Return a converted domain list using Pet Entity list")
    void testPetEntityListToDomainList() {
        List<PetEntity> petEntityList = Arrays.asList(this.buildPetEntity(), this.buildPetEntity());

        List<Pet> petDomainList = petInfraMapper.toDomain(petEntityList);

        assertEquals(2, petDomainList.size());

        PetEntity petEntityA = petEntityList.stream().findAny().orElse(new PetEntity());
        assertTrue(petDomainList.stream().anyMatch(pet -> pet.getId().equals(petEntityA.getId())),
                "Fail to find same UUID between entity list and domain list items");

    }

    private Pet buildPetDomain() {
        return Pet.builder()
                .id(UUID.randomUUID())
                .name("Doggo " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .breed("Brazilian Caramel Dog")
                .foodSet(Collections.singleton(
                        Food.builder()
                                .id(UUID.randomUUID())
                                .name("Biscrok")
                                .build()
                ))
                .build();
    }

    private PetEntity buildPetEntity() {
        return PetEntity.builder()
                .id(UUID.randomUUID())
                .name("Tofu " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .breed("Burmilla")
                .foodEntitySet(Collections.singleton(
                        FoodEntity.builder()
                                .id(UUID.randomUUID())
                                .name("Whiskas")
                                .build()
                ))
                .build();
    }
}