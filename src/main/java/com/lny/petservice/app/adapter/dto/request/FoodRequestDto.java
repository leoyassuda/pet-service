package com.lny.petservice.app.adapter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodRequestDto {

    private UUID id;
    private String name;
    private PetRequestDto petRequestDto;

}
