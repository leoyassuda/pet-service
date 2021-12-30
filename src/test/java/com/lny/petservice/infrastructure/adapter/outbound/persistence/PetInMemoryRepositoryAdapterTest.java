package com.lny.petservice.infrastructure.adapter.outbound.persistence;

import com.lny.petservice.domain.model.Food;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.infrastructure.mapper.FoodInfraMapper;
import com.lny.petservice.infrastructure.mapper.PetInfraMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Pet Repository In Memory Adapter")
@IndicativeSentencesGeneration(separator = " -> ")
class PetInMemoryRepositoryAdapterTest {

    @Spy
    private static FoodInfraMapper foodInfraMapper;

    @InjectMocks
    private PetInfraMapper petInfraMapper = Mappers.getMapper(PetInfraMapper.class);

    @Spy
    private PetInMemoryRepositoryAdapter petInMemoryRepositoryAdapter = new PetInMemoryRepositoryAdapter(petInfraMapper, new HashMap<>());

    @BeforeAll
    static void setUpAll() {
        foodInfraMapper = Mockito.spy(Mappers.getMapper(FoodInfraMapper.class));
    }

    @BeforeEach
    void setUp() {
//        petInMemoryRepositoryAdapter =
    }

    @Test
    @DisplayName("Return a Pet Domain when save a new Pet")
    void canSaveANewPetAndCheckIfContainsAValidGeneratedId() {
        Pet petToSave = Pet.builder()
                .name("New Pet 1")
                .breed("Breed 1")
                .foodSet(
                        Collections.singleton(
                                Food.builder()
                                        .name("Food 1")
                                        .build()
                        )
                )
                .build();
        Pet petSaved = petInMemoryRepositoryAdapter.save(petToSave);
        assertNotNull(petSaved.getId());
        assertEquals("New Pet 1", petSaved.getName());
        assertEquals(1, petSaved.getFoodSet().size());
    }

    @Test
    @DisplayName("Execute a delete using a valid Pet Domain and check if is list is empty")
    void canDeleteUsingPetDomainWithNoErrors() {
        Pet petToSave = Pet.builder()
                .name("New Pet 1")
                .breed("Breed 1")
                .foodSet(
                        Collections.singleton(
                                Food.builder()
                                        .name("Food 1")
                                        .build()
                        )
                )
                .build();
        Pet petSaved = petInMemoryRepositoryAdapter.save(petToSave);
        petInMemoryRepositoryAdapter.delete(petSaved);

        List<Pet> petList = petInMemoryRepositoryAdapter.getAll(1);

        assertTrue(petList.isEmpty());
        Mockito.verify(petInMemoryRepositoryAdapter).delete(petSaved);
    }

    @Test
    @DisplayName("Return pet find by ID")
    void canFindPetByIdWhenExists() {
        Pet petToSave = this.buildPetDomain();
        petToSave.setId(null);
        Pet petSaved = petInMemoryRepositoryAdapter.save(petToSave);
        UUID idFromPetSaved = petSaved.getId();

        Optional<Pet> petOptional = petInMemoryRepositoryAdapter.findById(idFromPetSaved);
        Pet petToTest = petOptional.orElse(new Pet());
        assertEquals(idFromPetSaved, petToTest.getId());
        assertEquals(1, petToTest.getFoodSet().size());
    }

    @Test
    @DisplayName("Return a valid list with Pet Domain objects")
    void canRetrieveAValidPetList() {
        Pet pet1 = this.buildPetDomain();
        pet1.setId(null);
        Pet pet2 = this.buildPetDomain();
        pet2.setId(null);
        Pet pet3 = this.buildPetDomain();
        pet3.setId(null);

        petInMemoryRepositoryAdapter.save(pet1);
        petInMemoryRepositoryAdapter.save(pet2);
        petInMemoryRepositoryAdapter.save(pet3);

        List<Pet> petList = petInMemoryRepositoryAdapter.getAll(1);

        assertEquals(3, petList.size());
        for (Pet pet : petList) {
            assertNotNull(pet.getId());
        }
    }

    private Pet buildPetDomain() {
        return Pet.builder()
                .id(UUID.randomUUID())
                .name("Doggo " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .breed("Brazilian Caramel Dog")
                .foodSet(Collections.singleton(
                        Food.builder()
                                .id(UUID.randomUUID())
                                .name("Biscrok")
                                .build()
                ))
                .build();
    }
}