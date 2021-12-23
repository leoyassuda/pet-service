package com.lny.petservice.infrastructure.adapter;

import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.out.PersistPet;
import com.lny.petservice.domain.port.out.RetrievePet;
import com.lny.petservice.infrastructure.adapter.read.PetReadRepository;
import com.lny.petservice.infrastructure.adapter.write.PetWriteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class PetRepositoryImpl implements PersistPet, RetrievePet {

    private final PetReadRepository petReadRepository;
    private final PetWriteRepository petWriteRepository;


    @Override
    public Pet save(Pet pet) {
        return petWriteRepository.save(pet);
    }

    @Override
    public Optional<Pet> findById(UUID id) {
        return petReadRepository.findById(id);
    }

    @Override
    public List<Pet> getAll() {
        return petReadRepository.findAll();
    }
}
