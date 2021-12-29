package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.repository.PetRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.PetRepositoryRetrievePort;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.read.PetReadRepository;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.write.PetWriteRepository;
import com.lny.petservice.infrastructure.mapper.PetInfraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequiredArgsConstructor
public class PetDbRepositoryImpl implements PetRepositoryPersistPort, PetRepositoryRetrievePort {


    private final PetReadRepository petReadRepository;
    private final PetWriteRepository petWriteRepository;
    private final PetInfraMapper petInfraMapper;

    @Value("${data.repository.pageSize:10}")
    private int pageSize;

    @Override
    public Pet save(Pet pet) {
        PetEntity petSaved = petWriteRepository.save(petInfraMapper.toEntity(pet));
        return petInfraMapper.toDomain(petSaved);
    }

    @Override
    public void delete(Pet pet) {
        petWriteRepository.delete(petInfraMapper.toEntity(pet));
    }

    @Override
    public Optional<Pet> findById(UUID id) {
        Optional<PetEntity> petEntity = petReadRepository.findById(id);
        return petEntity.map(petInfraMapper::toDomain);
    }

    @Override
    public List<Pet> getAll(int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<PetEntity> petEntityPage = petReadRepository.findAll(pageable);
        return petEntityPage.map(petInfraMapper::toDomain).getContent();
    }
}
