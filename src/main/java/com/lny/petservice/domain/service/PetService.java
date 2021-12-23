package com.lny.petservice.domain.service;

import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.in.DataPet;
import com.lny.petservice.domain.port.out.PersistPet;
import com.lny.petservice.domain.port.out.RetrievePet;
import com.lny.petservice.dto.PetRequestDto;
import com.lny.petservice.dto.PetResponseDto;
import com.lny.petservice.mapper.PetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService implements DataPet {

    private final PetMapper petMapper;

    private final RetrievePet retrievePet;
    private final PersistPet persistPet;

    @Override
    public PetResponseDto getPetById(UUID id) {
        var pet = retrievePet.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Pet with id %s not found", id)));
        return petMapper.toDto(pet);
    }

    @Override
    public PetResponseDto createPet(PetRequestDto petRequestDto){
        var pet = persistPet.save(petMapper.toEntity(petRequestDto));
        return petMapper.toDto(pet);
    }

    public List<PetResponseDto> getAllPets() {
        var pets = retrievePet.getAll();
        return petMapper.toDto(pets);
    }

    @Override
    public int countFood(UUID id) {
        var pet = retrievePet.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Pet count food error - id=%s", id)));
        return pet.getFoods().size();
    }

    @Override
    public boolean changeName(UUID id, String newName) {
        var pet = retrievePet.findById(id)
                .orElseThrow(()-> new RuntimeException(String.format("Pet not fount - id=%s", id)));
        pet.setName(newName);
        try{
            persistPet.save(pet);
        } catch (Exception ex){
            log.error("Error to update pet name!", ex);
            return false;
        }
        return true;
    }

    @Override
    public List<PetResponseDto> listPets() {
        var pets = retrievePet.getAll();
        return petMapper.toDto(pets);
    }
}
