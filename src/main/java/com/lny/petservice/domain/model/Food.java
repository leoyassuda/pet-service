package com.lny.petservice.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Food {

    private UUID id;
    private String name;
    private Pet pet;

}
