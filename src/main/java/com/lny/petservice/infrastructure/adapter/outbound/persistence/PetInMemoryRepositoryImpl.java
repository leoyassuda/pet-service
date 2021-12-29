package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.repository.PetRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.PetRepositoryRetrievePort;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import com.lny.petservice.infrastructure.mapper.PetInfraMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Slf4j
@Repository
@AllArgsConstructor
public class PetInMemoryRepositoryImpl implements PetRepositoryPersistPort, PetRepositoryRetrievePort {

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
        return Optional.of(petInfraMapper.toDomain(petEntity));
    }

    @Override
    public List<Pet> getAll(int page) {
        log.warn("Returning from memory, page is not implemented");
        ArrayList<PetEntity> petEntityArrayList = new ArrayList<>(petHashMap.values());
        return petInfraMapper.toDomain(petEntityArrayList);
    }

}
