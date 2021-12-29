package com.lny.petservice.app.adapter.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodResponseDto {

    private UUID id;
    private String name;
    @JsonIgnore
    private PetResponseDto petResponseDto;

}
