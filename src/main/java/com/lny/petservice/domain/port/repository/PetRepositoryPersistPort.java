package com.lny.petservice.domain.port.repository;

import com.lny.petservice.domain.model.Pet;

public interface PetRepositoryPersistPort {

    Pet save(Pet pet);

    void delete(Pet pet);

}
