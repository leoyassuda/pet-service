package com.lny.petservice.controller;

import com.lny.petservice.domain.port.in.DataPet;
import com.lny.petservice.dto.PetRequestDto;
import com.lny.petservice.dto.PetResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pets")
public class PetController {

    private final DataPet dataPetUseCase;

    @GetMapping
    public ResponseEntity<List<PetResponseDto>> getPets() {
        log.info("Getting all pets");
        return ResponseEntity.ok(dataPetUseCase.listPets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDto> getPet(@PathVariable(value = "id") UUID id) {
        log.info("Getting Pet by id {}", id);
        return ResponseEntity.ok(dataPetUseCase.getPetById(id));
    }

    @PostMapping
    public ResponseEntity<PetResponseDto> createPet(@RequestBody @Valid PetRequestDto petRequestDto) {
        log.info("Creating new Pet : {}", petRequestDto);
        return ResponseEntity.ok(dataPetUseCase.createPet(petRequestDto));
    }
}
