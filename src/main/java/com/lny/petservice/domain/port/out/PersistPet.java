package com.lny.petservice.domain.port.out;

import com.lny.petservice.domain.model.Pet;

public interface PersistPet {

    Pet save(Pet pet);

}
