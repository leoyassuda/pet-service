package com.lny.petservice.app.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PetRequestDto implements Serializable {

    @Length(min = 3, max = 50, message = "Name '${validatedValue}' must be between {min} and {max} length")
    @NotNull(message = "Name can not be null")
    private String name;

    @Length(min = 3, max = 50, message = "Breed '${validatedValue}' must be between {min} and {max} length")
    @NotNull(message = "Breed can not be null")
    private String breed;

}
