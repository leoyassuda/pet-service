package com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.write;

import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodWriteRepository extends JpaRepository<FoodEntity, UUID> {
}
