package com.lny.petservice.domain.port.repository;

import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;

public interface PetRepositoryPersistPort {

    PetEntity save(PetEntity petEntity);

    void delete(PetEntity petEntity);

}
