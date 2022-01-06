package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import com.lny.petservice.domain.model.Food;
import com.lny.petservice.domain.port.repository.FoodRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.FoodRepositoryRetrievePort;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.FoodEntity;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.read.FoodReadRepository;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.write.FoodWriteRepository;
import com.lny.petservice.infrastructure.mapper.FoodInfraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Transactional
@RequiredArgsConstructor
public class FoodDbRepositoryAdapter implements FoodRepositoryPersistPort, FoodRepositoryRetrievePort {

    private final FoodReadRepository foodReadRepository;
    private final FoodWriteRepository foodWriteRepository;
    private final FoodInfraMapper foodInfraMapper;

    @Value("${data.repository.pageSize:10}")
    private int pageSize;

    @Override
    public Food save(Food food) {
        FoodEntity foodSaved = foodWriteRepository.save(foodInfraMapper.toEntity(food));
        return foodInfraMapper.toDomain(foodSaved);
    }

    @Override
    public void delete(Food food) {
        foodWriteRepository.delete(foodInfraMapper.toEntity(food));
    }

    @Override
    public Optional<Food> findById(UUID id) {
        Optional<FoodEntity> optionalFoodEntity = foodReadRepository.findById(id);
        if (optionalFoodEntity.isEmpty()) return Optional.empty();
        FoodEntity foodEntity = optionalFoodEntity.get();
        return Optional.of(foodInfraMapper.toDomain(foodEntity));
    }

    @Override
    public List<Food> getAll(int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<FoodEntity> foodEntityPage = foodReadRepository.findAll(pageable);
        return foodEntityPage.map(foodInfraMapper::toDomain).getContent();
    }

}
