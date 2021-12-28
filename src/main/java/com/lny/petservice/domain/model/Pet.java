package com.lny.petservice.domain.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Pet {

    private UUID id;
    private String name;
    private String breed;
    private Set<Food> foodSet = new HashSet<>();

}
