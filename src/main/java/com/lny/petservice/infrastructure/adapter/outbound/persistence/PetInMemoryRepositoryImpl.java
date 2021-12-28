package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import com.lny.petservice.domain.port.repository.PetRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.PetRepositoryRetrievePort;
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

    private HashMap<UUID, PetEntity> petHashMap;

    @Override
    public PetEntity save(PetEntity petEntity) {
        if (petEntity.getId() == null) {
            petEntity.setId(randomUUID());
        }

        petHashMap.put(petEntity.getId(), petEntity);
        return petHashMap.get(petEntity.getId());
    }

    @Override
    public void delete(PetEntity petEntity) {
        petHashMap.remove(petEntity.getId());
    }

    @Override
    public Optional<PetEntity> findById(UUID id) {
        PetEntity petEntity = petHashMap.get(id);
        if (petEntity == null) return Optional.empty();
        return Optional.of(petEntity);
    }

    @Override
    public List<PetEntity> getAll(int page) {
        log.warn("Returning from memory, page is not implemented");
        return new ArrayList<>(petHashMap.values());
    }

}
