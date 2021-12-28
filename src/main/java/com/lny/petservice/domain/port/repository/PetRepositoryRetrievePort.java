package com.lny.petservice.domain.port.repository;

import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepositoryRetrievePort {

    Optional<PetEntity> findById(UUID id);

    List<PetEntity> getAll(int page);
}
