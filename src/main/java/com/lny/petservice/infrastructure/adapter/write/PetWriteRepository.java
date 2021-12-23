package com.lny.petservice.infrastructure.adapter.write;

import com.lny.petservice.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PetWriteRepository extends JpaRepository<Pet, UUID> {}
