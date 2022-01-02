package com.lny.petservice.domain.adapter.service;

import com.lny.petservice.domain.model.Food;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.repository.PetRepositoryPersistPort;
import com.lny.petservice.domain.port.repository.PetRepositoryRetrievePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Pet Service Adapter")
@IndicativeSentencesGeneration(separator = " -> ")
class PetServiceServiceAdapterTest {

    @Mock
    private PetRepositoryRetrievePort petRepositoryRetrievePort;

    @Mock
    private PetRepositoryPersistPort petRepositoryPersistPort;

    @InjectMocks
    private PetServiceServiceAdapter petServiceServiceAdapter;

    private final UUID petId = UUID.fromString("c43b78aa-8c89-42fb-a647-766847536fb6");

    @Test
    @DisplayName("Return a valid Pet when find by Id")
    void canRetrievePetById() {
        // given
        given(petRepositoryRetrievePort.findById(any(UUID.class)))
                .willReturn(Optional.of(this.petDomainBuild(petId)));

        // when
        Pet pet = petServiceServiceAdapter.getPetById(petId);

        // then
        assertEquals(petId, pet.getId(), "Fail to response same pet id");
        assertEquals(1, pet.getFoodSet().size(), "Fail to map foodSet in Pet");
    }

    @Test
    @DisplayName("Return a new Pet with valid Id when call create pet")
    void canRetrieveNewPetAfterCreateWithSuccess() {
        Pet pet = this.petDomainBuild(null);
        pet.setId(null);

        // given
        given(petRepositoryPersistPort.save(any(Pet.class)))
                .willReturn(this.petDomainBuild(petId));

        // when
        Pet petCreated = petServiceServiceAdapter.createPet(pet);

        // then
        assertEquals(petId, petCreated.getId(), "Fail to response same pet id");
        assertEquals(1, petCreated.getFoodSet().size(), "Fail to map foodSet in Pet");
    }

    @Test
    @DisplayName("Return a int with total of foods a pet is associated")
    void canRetrieveTheNumberOfFoodAssociatedAPet() {
        Set<Food> foodSet = new HashSet<>();
        foodSet.add(this.foodDomainBuild());
        foodSet.add(this.foodDomainBuild());
        foodSet.add(this.foodDomainBuild());

        Pet petWithThreeFoods = Pet.builder()
                .id(UUID.randomUUID())
                .name("Big Doggo")
                .breed("Brazilian Caramel Dog")
                .foodSet(foodSet)
                .build();
        // given
        given(petRepositoryRetrievePort.findById(any(UUID.class)))
                .willReturn(Optional.of(petWithThreeFoods));

        // when
        int countFood = petServiceServiceAdapter.countFood(petId);

        // then
        assertEquals(3, countFood, "Fail to count pet's food");
    }

    @Test
    @DisplayName("Return a Pet with new name when call change name method")
    void canReturnAPetWithNewNameWhenChangeName() {
        Pet pet = this.petDomainBuild(petId);
        Pet petNewName = this.petDomainBuild(petId);
        petNewName.setName("Azulão");

        // given
        given(petRepositoryRetrievePort.findById(petId))
                .willReturn(Optional.of(pet));
        given(petRepositoryPersistPort.save(any(Pet.class)))
                .willReturn(petNewName);

        // when
        Pet petAzulao = petServiceServiceAdapter.changeName(petId, "Azulão");

        // then
        assertEquals("Azulão", petAzulao.getName(), "Fail to rename pet");
    }

    @Test
    @DisplayName("Return a valid list of pets when call list all pets")
    void canListPets() {
        Pet pet1 = this.petDomainBuild(null);
        Pet pet2 = this.petDomainBuild(null);
        Pet pet3 = this.petDomainBuild(null);
        List<Pet> petList = Arrays.asList(pet1, pet2, pet3);

        // given
        given(petRepositoryRetrievePort.getAll(anyInt()))
                .willReturn(petList);

        // when
        List<Pet> pets = petServiceServiceAdapter.listPets(1);

        // then
        assertEquals(3, pets.size(), "Fail to return 3 pets in list");
    }

    private Pet petDomainBuild(UUID id) {
        return Pet.builder()
                .id(id == null ? UUID.randomUUID() : id)
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

    private Food foodDomainBuild() {
        return Food.builder()
                .id(UUID.randomUUID())
                .name("Food " + (int) (Math.random() * (30 - 1 + 1) + 1))
                .build();
    }
}