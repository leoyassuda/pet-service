package com.lny.petservice.domain.adapter.service;

import com.lny.petservice.common.error.InvalidOperationException;
import com.lny.petservice.common.error.ResourceNotFoundException;
import com.lny.petservice.domain.mapper.PetDomainMapper;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.repository.PetRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.PetRepositoryRetrievePort;
import com.lny.petservice.domain.port.service.PetServicePort;
import com.lny.petservice.infrastructure.mapper.PetPersistMapper;
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

    private final PetPersistMapper petPersistMapper;
    private final PetDomainMapper petDomainMapper;

    @Override
    public Pet getPetById(UUID id) {
        var petEntity = petRepositoryRetrievePort.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Pet with id %s not found", id)));
        return petDomainMapper.toDomain(petEntity);
    }

    @Override
    public Pet createPet(Pet pet) {
        var petEntity = petRepositoryPersistPort.save(petPersistMapper.toEntity(pet));
        return petDomainMapper.toDomain(petEntity);
    }

    @Override
    public int countFood(UUID id) {
        var petEntity = petRepositoryRetrievePort.findById(id)
                .orElseThrow(() -> new InvalidOperationException(String.format("Pet count food error - id=%s", id)));
        return petEntity.getFoodEntitySet().size();
    }

    @Override
    public boolean changeName(UUID id, String newName) {
        var petEntity = petRepositoryRetrievePort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Pet not fount - id=%s", id)));
        petEntity.setName(newName);
        try {
            petRepositoryPersistPort.save(petEntity);
        } catch (Exception ex) {
            log.error("Error to update petEntity name!", ex);
            return false;
        }
        return true;
    }

    @Override
    public List<Pet> listPets(int page) {
        var petEntityList = petRepositoryRetrievePort.getAll(page);
        return petDomainMapper.toDomain(petEntityList);
    }
}
