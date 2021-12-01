package com.lny.petservice.service;

import com.lny.petservice.dto.PetRequestDto;
import com.lny.petservice.dto.PetResponseDto;
import com.lny.petservice.mapper.PetMapper;
import com.lny.petservice.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public PetResponseDto getPetById(UUID id) {
        var pet = petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Pet with id %s not found", id)));
        return petMapper.toDto(pet);
    }

    public PetResponseDto createPet(PetRequestDto petRequestDto){
        var pet = petRepository.save(petMapper.toEntity(petRequestDto));
        return petMapper.toDto(pet);
    }

}
