package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import com.lny.petservice.domain.port.repository.PetRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.PetRepositoryRetrievePort;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.entity.PetEntity;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.read.PetReadRepository;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.write.PetWriteRepository;
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

    @Value("${data.repository.pageSize:10}")
    private int pageSize;

    @Override
    public PetEntity save(PetEntity petEntity) {
        return petWriteRepository.save(petEntity);
    }

    @Override
    public void delete(PetEntity petEntity) {
        petWriteRepository.delete(petEntity);
    }

    @Override
    public Optional<PetEntity> findById(UUID id) {
        return petReadRepository.findById(id);
    }

    @Override
    public List<PetEntity> getAll(int page) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<PetEntity> petEntityPage = petReadRepository.findAll(pageable);
        return petEntityPage.getContent();
    }
}
