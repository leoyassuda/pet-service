package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import com.lny.petservice.common.error.InvalidOperationException;
import com.lny.petservice.domain.model.Food;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.repository.PetRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.PetRepositoryRetrievePort;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.FoodEntity;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.read.PetReadRepository;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.write.FoodWriteRepository;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.write.PetWriteRepository;
import com.lny.petservice.infrastructure.mapper.FoodInfraMapper;
import com.lny.petservice.infrastructure.mapper.PetInfraMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class PetDbRepositoryAdapter implements PetRepositoryPersistPort, PetRepositoryRetrievePort {

    private final PetReadRepository petReadRepository;
    private final PetWriteRepository petWriteRepository;
    private final FoodWriteRepository foodWriteRepository;
    private final PetInfraMapper petInfraMapper;
    private final FoodInfraMapper foodInfraMapper;

    @Value("${data.repository.pageSize:10}")
    private int pageSize;

    @Override
    public Pet save(Pet pet) {
        log.info("Saving Pet={}", pet);
        PetEntity petEntity = petInfraMapper.toEntity(pet);
        petEntity.setFoodEntitySet(null);
        PetEntity petSaved = petWriteRepository.save(petEntity);

        Set<Food> foodSet = pet.getFoodSet();
        if (CollectionUtils.isEmpty(foodSet)) {
            return this.findById(petSaved.getId())
                    .orElseThrow(() -> new InvalidOperationException("Error to save Pet"));
        }

        log.info("Saving pet's foods ...");
        for (Food food : foodSet) {
            FoodEntity foodEntity = foodInfraMapper.toEntity(food);
            foodEntity.setPetEntity(petEntity);
            foodWriteRepository.save(foodEntity);
        }

        return this.findByIdWithFoods(petSaved.getId())
                .orElseThrow(() -> new InvalidOperationException("Error to save Pet with foods"));
    }

    @Override
    public void delete(Pet pet) {
        log.info("Deleting pet={}", pet);
        petWriteRepository.delete(petInfraMapper.toEntity(pet));
    }

    @Override
    public Optional<Pet> findById(UUID id) {
        log.info("Finding pet with id={}", id);
        Optional<PetEntity> optionalPetEntity = petReadRepository.findById(id);
        if (optionalPetEntity.isEmpty()) return Optional.empty();
        PetEntity petEntity = optionalPetEntity.get();
        return Optional.of(petInfraMapper.toDomainSkipFood(petEntity));
    }

    @Override
    public Optional<Pet> findByIdWithFoods(UUID id) {
        log.info("Finding pet with id={}", id);
        Optional<PetEntity> optionalPetEntity = petReadRepository.findByIdWithFoods(id);
        if (optionalPetEntity.isEmpty()) return Optional.empty();
        PetEntity petEntity = optionalPetEntity.get();
        return Optional.of(petInfraMapper.toDomain(petEntity));
    }

    @Override
    public List<Pet> getAll(int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<PetEntity> petEntityPage = petReadRepository.findAll(pageable);
        return petEntityPage.map(petEntity -> {
            if (petEntity.getFoodEntitySet() == null) return petInfraMapper.toDomainSkipFood(petEntity);
            return petInfraMapper.toDomain(petEntity);
        }).getContent();
    }

}
