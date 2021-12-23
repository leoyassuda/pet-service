package com.lny.petservice.domain.port.in;

import com.lny.petservice.dto.PetRequestDto;
import com.lny.petservice.dto.PetResponseDto;

import java.util.List;
import java.util.UUID;

public interface DataPet {

    int countFood(UUID id);

    boolean changeName(UUID id, String newName);

    List<PetResponseDto> listPets();

    PetResponseDto getPetById(UUID id);

    PetResponseDto createPet(PetRequestDto petRequestDto);

}
