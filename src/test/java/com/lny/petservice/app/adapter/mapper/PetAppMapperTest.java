package com.lny.petservice.app.adapter.mapper;

import com.lny.petservice.app.adapter.dto.request.FoodRequestDto;
import com.lny.petservice.app.adapter.dto.request.PetRequestDto;
import com.lny.petservice.app.adapter.dto.response.FoodResponseDto;
import com.lny.petservice.app.adapter.dto.response.PetResponseDto;
import com.lny.petservice.domain.model.Food;
import com.lny.petservice.domain.model.Pet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Pet mapper in Application module")
@IndicativeSentencesGeneration(separator = " -> ")
class PetAppMapperTest {

    @Spy
    private static FoodAppMapper foodAppMapper;

    @InjectMocks
    private PetAppMapper petAppMapper = Mappers.getMapper(PetAppMapper.class);

    @BeforeAll
    static void setUp() {
        foodAppMapper = Mockito.spy(Mappers.getMapper(FoodAppMapper.class));
    }

    @Test
    @DisplayName("Return equals values between Pet Domain and Pet Response using MapStruct mapper")
    void testDomainPetObjectToResponseObject() {

        Pet pet = this.buildPetDomain();

        PetResponseDto petResponseDto = petAppMapper.toResponseDto(pet);

        assertEquals(pet.getId(), petResponseDto.getId());
        assertEquals(pet.getName(), petResponseDto.getName());
        assertEquals(pet.getBreed(), petResponseDto.getBreed());
        assertEquals(pet.getFoodSet().size(), petResponseDto.getFoodResponseDtoSet().size());

        Food food = pet.getFoodSet().stream().findFirst().orElse(new Food());
        FoodResponseDto foodResponseDto = petResponseDto.getFoodResponseDtoSet().stream().findFirst().orElse(new FoodResponseDto());

        assertEquals(food.getId(), foodResponseDto.getId());
        assertEquals(food.getName(), foodResponseDto.getName());
        assertNull(foodResponseDto.getPetResponseDto());

    }

    @Test
    @DisplayName("Return a converted response list using Pet Domain list")
    void testPetDomainListToResponseList() {
        List<Pet> petList = Arrays.asList(this.buildPetDomain(), this.buildPetDomain());

        List<PetResponseDto> petResponseDtoList = petAppMapper.toResponseDto(petList);

        assertEquals(2, petResponseDtoList.size());

        Pet petA = petList.stream().findAny().orElse(new Pet());
        assertTrue(petResponseDtoList.stream().anyMatch(petResponseDto -> petResponseDto.getId().equals(petA.getId())),
                "Fail to find same UUID between domain list and response list items");

    }

    @Test
    @DisplayName("Return equals values between Pet Request and Pet Domain using MapStruct mapper")
    void testRequestPetObjectToDomainObject() {

        PetRequestDto petRequestDto = this.buildPetRequest();

        Pet petDomain = petAppMapper.toDomain(petRequestDto);

        assertEquals(petRequestDto.getId(), petDomain.getId());
        assertEquals(petRequestDto.getName(), petDomain.getName());
        assertEquals(petRequestDto.getBreed(), petDomain.getBreed());
        assertEquals(petRequestDto.getFoodRequestDtoSet().size(), petDomain.getFoodSet().size());

        FoodRequestDto foodRequestDto = petRequestDto.getFoodRequestDtoSet().stream().findFirst().orElse(new FoodRequestDto());
        Food food = petDomain.getFoodSet().stream().findFirst().orElse(new Food());

        assertEquals(foodRequestDto.getId(), food.getId());
        assertEquals(foodRequestDto.getName(), food.getName());
        assertNull(food.getPet());

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

    private PetResponseDto buildPetResponse() {
        return PetResponseDto.builder()
                .id(UUID.randomUUID())
                .name("Doggo " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .breed("Brazilian Caramel Dog")
                .foodResponseDtoSet(Collections.singleton(
                        FoodResponseDto.builder()
                                .id(UUID.randomUUID())
                                .name("Biscrok")
                                .build()
                ))
                .build();
    }

    private PetRequestDto buildPetRequest(){
        return PetRequestDto.builder()
                .id(UUID.randomUUID())
                .name("Doggo " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .breed("Brazilian Caramel Dog")
                .foodRequestDtoSet(Collections.singleton(
                        FoodRequestDto.builder()
                                .id(UUID.randomUUID())
                                .name("Biscrok")
                                .build()
                ))
                .build();
    }

}