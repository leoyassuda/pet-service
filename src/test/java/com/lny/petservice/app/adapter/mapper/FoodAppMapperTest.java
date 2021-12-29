package com.lny.petservice.app.adapter.mapper;

import com.lny.petservice.app.adapter.dto.request.FoodRequestDto;
import com.lny.petservice.app.adapter.dto.response.FoodResponseDto;
import com.lny.petservice.domain.model.Food;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Food mapper in Application module")
@IndicativeSentencesGeneration(separator = " -> ")
class FoodAppMapperTest {

    private final FoodAppMapper foodAppMapper = Mappers.getMapper(FoodAppMapper.class);

    @Test
    @DisplayName("Return equals values between Food Domain and Food Response using MapStruct mapper")
    void testDomainFoodObjectToResponseObject() {

        Food food = this.buildFoodDomain();

        FoodResponseDto foodResponseDto = foodAppMapper.toResponseDto(food);

        assertEquals(food.getId(), foodResponseDto.getId());
        assertEquals(food.getName(), foodResponseDto.getName());

        assertNull(foodResponseDto.getPetResponseDto());

    }

    @Test
    @DisplayName("Return a converted response list using Food Domain list")
    void testFoodDomainListToResponseList() {
        Set<Food> foodSet = new HashSet<>();
        foodSet.add(this.buildFoodDomain());
        foodSet.add(this.buildFoodDomain());

        Set<FoodResponseDto> foodResponseDtoSet = foodAppMapper.toResponseDto(foodSet);

        assertEquals(2, foodResponseDtoSet.size());

        Food food1 = foodSet.stream().findAny().orElse(new Food());
        assertTrue(foodResponseDtoSet.stream().anyMatch(foodResponseDto -> foodResponseDto.getId().equals(food1.getId())),
                "Fail to find same UUID between domain list and response list");

    }

    @Test
    @DisplayName("Return equals values between Food Domain and Food Response using MapStruct mapper")
    void testRequestFoodObjectToDomainObject(){
        FoodRequestDto foodRequestDto = this.buildFoodRequest();

        Food food = foodAppMapper.toDomain(foodRequestDto);

        assertEquals(foodRequestDto.getId(), food.getId());
        assertEquals(foodRequestDto.getName(), food.getName());

        assertNull(food.getPet());
    }

    // TODO: Req to Domain - Set
    @Test
    @DisplayName("Return a converted domain list using Food Request list")
    void testFoodRequestListToDomainList() {
        Set<FoodRequestDto> requestDtoHashSet = new HashSet<>();
        requestDtoHashSet.add(this.buildFoodRequest());
        requestDtoHashSet.add(this.buildFoodRequest());

        Set<Food> foodSet = foodAppMapper.toDomain(requestDtoHashSet);

        assertEquals(2, foodSet.size());

        FoodRequestDto foodRequestDto = requestDtoHashSet.stream().findAny().orElse(new FoodRequestDto());
        assertTrue(foodSet.stream().anyMatch(foodResponseDto -> foodResponseDto.getId().equals(foodRequestDto.getId())),
                "Fail to find same UUID between domain list and request list");

    }

    private Food buildFoodDomain() {
        return Food.builder()
                .id(UUID.randomUUID())
                .name("Snack " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .build();
    }

    private FoodRequestDto buildFoodRequest() {
        return FoodRequestDto.builder()
                .id(UUID.randomUUID())
                .name("Snack " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .build();
    }

}