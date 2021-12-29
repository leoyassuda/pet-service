package com.lny.petservice.app.adapter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponseDto {

    private UUID id;
    private String name;
    private String breed;
    private Set<FoodResponseDto> foodResponseDtoSet;

}
