package com.lny.petservice.repository;

import com.lny.petservice.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {

    @Override
    Optional<Pet> findById(UUID id);
}
