package com.lny.petservice.domain.adapter.service;

import com.lny.petservice.common.error.InvalidOperationException;
import com.lny.petservice.common.error.ResourceNotFoundException;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.repository.PetRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.PetRepositoryRetrievePort;
import com.lny.petservice.domain.port.service.PetServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceServiceImpl implements PetServicePort {

    private final PetRepositoryRetrievePort petRepositoryRetrievePort;
    private final PetRepositoryPersistPort petRepositoryPersistPort;

    @Override
    public Pet getPetById(UUID id) {
        return petRepositoryRetrievePort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Pet with id %s not found", id)));
    }

    @Override
    public Pet createPet(Pet pet) {
        return petRepositoryPersistPort.save(pet);
    }

    @Override
    public int countFood(UUID id) {
        var pet = petRepositoryRetrievePort.findById(id)
                .orElseThrow(() -> new InvalidOperationException(String.format("Pet count food error - id=%s", id)));
        return pet.getFoodSet().size();
    }

    @Override
    public boolean changeName(UUID id, String newName) {
        var pet = petRepositoryRetrievePort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Pet not fount - id=%s", id)));
        pet.setName(newName);
        try {
            petRepositoryPersistPort.save(pet);
        } catch (Exception ex) {
            log.error("Error to update petEntity name!", ex);
            return false;
        }
        return true;
    }

    @Override
    public List<Pet> listPets(int page) {
        return petRepositoryRetrievePort.getAll(page);
    }
}
