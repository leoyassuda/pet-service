package com.lny.petservice.domain.port.repository;

import com.lny.petservice.domain.model.Pet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepositoryRetrievePort {

    Optional<Pet> findById(UUID id);

    List<Pet> getAll(int page);
}
