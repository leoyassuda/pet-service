package com.lny.petservice.app.adapter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PetRequestDto {

    @Pattern(regexp = "^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
    private UUID id;

    @Length(min = 3, max = 50, message = "Name '${validatedValue}' must be between {min} and {max} length")
    @NotNull(message = "Name can not be null")
    private String name;

    @Length(min = 3, max = 50, message = "Breed '${validatedValue}' must be between {min} and {max} length")
    @NotNull(message = "Breed can not be null")
    private String breed;

    private Set<FoodRequestDto> foodRequestDtoSet;
}
