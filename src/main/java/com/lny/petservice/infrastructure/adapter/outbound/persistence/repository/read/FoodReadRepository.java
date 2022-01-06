package com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.read;

import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.FoodEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodReadRepository extends PagingAndSortingRepository<FoodEntity, UUID> {
}
