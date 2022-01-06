package com.lny.petservice.domain.port.repository;

import com.lny.petservice.domain.model.Food;

public interface FoodRepositoryPersistPort {

    Food save(Food pet);

    void delete(Food pet);

}
