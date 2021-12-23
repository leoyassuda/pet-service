package com.lny.petservice.domain.port.out;

import com.lny.petservice.domain.model.Pet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RetrievePet {

    Optional<Pet> findById(UUID id);

    List<Pet> getAll();
}
