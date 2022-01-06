package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.repository.PetRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.PetRepositoryRetrievePort;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import com.lny.petservice.infrastructure.mapper.PetInfraMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Slf4j
@Transactional
@Repository
@AllArgsConstructor
public class PetInMemoryRepositoryAdapter implements PetRepositoryPersistPort, PetRepositoryRetrievePort {

    private PetInfraMapper petInfraMapper;
    private HashMap<UUID, PetEntity> petHashMap;

    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(randomUUID());
        }

        petHashMap.put(pet.getId(), petInfraMapper.toEntity(pet));
        PetEntity petEntity = petHashMap.get(pet.getId());
        return petInfraMapper.toDomain(petEntity);
    }

    @Override
    public void delete(Pet pet) {
        petHashMap.remove(petInfraMapper.toEntity(pet).getId());
    }

    @Override
    public Optional<Pet> findById(UUID id) {
        PetEntity petEntity = petHashMap.get(id);
        if (petEntity == null) return Optional.empty();
        return Optional.of(petInfraMapper.toDomainSkipFood(petEntity));
    }

    @Override
    public Optional<Pet> findByIdWithFoods(UUID id) {
        PetEntity petEntity = petHashMap.get(id);
        if (petEntity == null) return Optional.empty();
        return Optional.of(petInfraMapper.toDomain(petEntity));
    }

    @Override
    public List<Pet> getAll(int page) {
        log.warn("Returning all values from memory, page is not implemented for memory adapter");
        ArrayList<PetEntity> petEntityArrayList = new ArrayList<>(petHashMap.values());
        return petInfraMapper.toDomain(petEntityArrayList);
    }

}
