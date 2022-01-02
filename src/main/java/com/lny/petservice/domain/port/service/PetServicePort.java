package com.lny.petservice.domain.port.service;

import com.lny.petservice.domain.model.Pet;

import java.util.List;
import java.util.UUID;

public interface PetServicePort {

    int countFood(UUID id);

    Pet changeName(UUID id, String newName);

    List<Pet> listPets(int page);

    Pet getPetById(UUID id);

    Pet createPet(Pet pet);

}
