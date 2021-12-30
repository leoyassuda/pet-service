package com.lny.petservice.app.adapter.inbound.web.controller;

import com.lny.petservice.app.adapter.dto.request.PetRequestDto;
import com.lny.petservice.app.adapter.dto.response.PetResponseDto;
import com.lny.petservice.app.adapter.mapper.PetAppMapper;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.service.PetServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetAppMapper petAppMapper;
    private final PetServicePort petServicePort;

    @GetMapping
    public ResponseEntity<List<PetResponseDto>> getPets(@RequestParam int page) {
        log.info("Getting all pets");
        return ResponseEntity.ok(petAppMapper.toResponseDto(petServicePort.listPets(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDto> getPet(@PathVariable(value = "id") UUID id) {
        log.info("Getting Pet by id {}", id);
        return ResponseEntity.ok(petAppMapper.toResponseDto(petServicePort.getPetById(id)));
    }

    @PostMapping
    public ResponseEntity<PetResponseDto> createPet(@RequestBody @Valid PetRequestDto petRequestDto) {
        log.info("Creating new Pet : {}", petRequestDto);
        Pet pet = petServicePort.createPet(petAppMapper.toDomain(petRequestDto));
        PetResponseDto petResponseDto = petAppMapper.toResponseDto(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(petResponseDto);
    }

    // TODO: add delete and put/patch methods
}
