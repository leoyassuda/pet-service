package com.lny.petservice.app.adapter.inbound.web.controller;

import com.lny.petservice.app.adapter.dto.PetRequestDto;
import com.lny.petservice.app.adapter.dto.PetResponseDto;
import com.lny.petservice.domain.mapper.PetDomainMapper;
import com.lny.petservice.app.adapter.mapper.PetPresenterMapper;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.service.PetServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final PetDomainMapper petDomainMapper;
    private final PetPresenterMapper petPresenterMapper;
    private final PetServicePort petServicePort;

    @GetMapping
    public ResponseEntity<List<PetResponseDto>> getPets(@RequestParam int page) {
        log.info("Getting all pets");
        return ResponseEntity.ok(petPresenterMapper.toDto(petServicePort.listPets(page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDto> getPet(@PathVariable(value = "id") UUID id) {
        log.info("Getting Pet by id {}", id);
        return ResponseEntity.ok(petPresenterMapper.toDto(petServicePort.getPetById(id)));
    }

    @PostMapping
    public ResponseEntity<PetResponseDto> createPet(@RequestBody @Valid PetRequestDto petRequestDto) {
        log.info("Creating new Pet : {}", petRequestDto);
        Pet pet = petServicePort.createPet(petDomainMapper.toDomain(petRequestDto));
        return ResponseEntity.ok(petPresenterMapper.toDto(pet));
    }
}
