package com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.read;

import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetReadRepository extends PagingAndSortingRepository<PetEntity, UUID> {

    @Query("Select p from PetEntity p join fetch p.foodEntitySet f where p.id = :id")
    Optional<PetEntity> findByIdWithFoods(@NonNull UUID id);
}
