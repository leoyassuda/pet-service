package com.lny.petservice.infrastructure.adapter.read;

import com.lny.petservice.domain.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface PetReadRepository extends JpaRepository<Pet, UUID> {}
