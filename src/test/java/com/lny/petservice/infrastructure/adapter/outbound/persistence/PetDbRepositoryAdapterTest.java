package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import com.lny.petservice.domain.model.Food;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.read.PetReadRepository;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.write.FoodWriteRepository;
import com.lny.petservice.infrastructure.adapter.outbound.persistence.repository.write.PetWriteRepository;
import com.lny.petservice.infrastructure.mapper.FoodInfraMapper;
import com.lny.petservice.infrastructure.mapper.PetInfraMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class PetDbRepositoryAdapterTest extends AbstractDbRepositoryIT {

    @Spy
    @Autowired
    private PetReadRepository petReadRepository;

    @Spy
    @Autowired
    private PetWriteRepository petWriteRepository;

    @Spy
    @Autowired
    private FoodWriteRepository foodWriteRepository;

    @Spy
    @Autowired
    private PetInfraMapper petInfraMapper;

    @Spy
    @Autowired
    private FoodInfraMapper foodInfraMapper;

    @InjectMocks
    private PetDbRepositoryAdapter petDbRepositoryAdapter;

    @BeforeEach
    void setUp() {
        petDbRepositoryAdapter = new PetDbRepositoryAdapter(petReadRepository,
                petWriteRepository,
                foodWriteRepository,
                petInfraMapper,
                foodInfraMapper);
    }

    @Test
    void save() {
        Pet pet = petDbRepositoryAdapter.save(Pet.builder()
                .name("Test 1")
                .breed("Breed Test 1")
                .build());

        System.out.println(pet.toString());
        assertNotNull(pet.getId());
    }

    //    @Test
//    void delete() {
//    }
//
    @Test
    void findById() {
        Pet pet = petDbRepositoryAdapter.save(Pet.builder()
                .name("Test 1")
                .breed("Breed Test 1")
                .foodSet(new HashSet<>())
                .build());

        Optional<Pet> optionalPet = petDbRepositoryAdapter.findById(pet.getId());
        Pet petTested = optionalPet.orElse(new Pet());
        assertNotNull(pet.getId());
        assertEquals(pet, petTested);
    }

    @Test
    void findByIdWithFoods() {
        Pet pet = Pet.builder()
                .name("Test 1 With Foods")
                .breed("Breed Test 1")
                .foodSet(Collections.singleton(
                        Food.builder()
                                .name("Food 1 Test")
                                .build()
                ))
                .build();

        Pet petSaved = petDbRepositoryAdapter.save(pet);


        Optional<Pet> optionalPet = petDbRepositoryAdapter.findByIdWithFoods(petSaved.getId());
        Pet petTested = optionalPet.orElse(new Pet());
        assertNotNull(petSaved.getId());
        assertEquals(petSaved, petTested);
        assertNotNull(petSaved.getFoodSet().stream().map(Food::getId));
    }
//
//    @Test
//    void getAll() {
//    }
}