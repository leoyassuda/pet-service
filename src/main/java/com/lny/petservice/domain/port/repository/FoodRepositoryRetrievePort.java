package com.lny.petservice.domain.port.repository;

import com.lny.petservice.domain.model.Food;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodRepositoryRetrievePort {

    Optional<Food> findById(UUID id);

    List<Food> getAll(int page);
}