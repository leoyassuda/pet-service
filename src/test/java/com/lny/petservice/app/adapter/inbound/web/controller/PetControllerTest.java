package com.lny.petservice.app.adapter.inbound.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lny.petservice.app.adapter.dto.request.FoodRequestDto;
import com.lny.petservice.app.adapter.dto.request.PetRequestDto;
import com.lny.petservice.app.adapter.dto.response.FoodResponseDto;
import com.lny.petservice.app.adapter.dto.response.PetResponseDto;
import com.lny.petservice.app.adapter.mapper.FoodAppMapper;
import com.lny.petservice.app.adapter.mapper.PetAppMapper;
import com.lny.petservice.app.adapter.mapper.PetAppMapperImpl;
import com.lny.petservice.common.error.handler.GlobalExceptionHandler;
import com.lny.petservice.domain.model.Food;
import com.lny.petservice.domain.model.Pet;
import com.lny.petservice.domain.port.service.PetServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test Pet Controller")
@IndicativeSentencesGeneration(separator = " -> ")
class PetControllerTest {

    private final UUID petId = UUID.fromString("c43b78aa-8c89-42fb-a647-766847536fb6");
    private final UUID foodId = UUID.fromString("b7b4fbdd-d2e6-42b4-844e-573bf60e9c04");

    private MockMvc mockMvc;

    @Mock
    private PetServicePort petServicePort;

    private JacksonTester<PetResponseDto> jsonPetResponse;
    private JacksonTester<List<PetResponseDto>> jsonPetResponseList;

    @BeforeEach
    void setUp() {
        FoodAppMapper foodAppMapper = Mappers.getMapper(FoodAppMapper.class);
        PetAppMapper petAppMapper = new PetAppMapperImpl(foodAppMapper);
        PetController petController = new PetController(petAppMapper, petServicePort);

        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(petController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Return Ok and a valid pet response when retrieve by Id")
    void canRetrieveByIdWhenExists() throws Exception {


        PetResponseDto petResponseDto = this.petResponseBuild();

        // given
        given(petServicePort.getPetById(petId))
                .willReturn(this.petDomainBuild());

        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/pets/" + petId)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus(), "Fail to response a OK status");
        assertEquals(jsonPetResponse.write(petResponseDto).getJson(), response.getContentAsString(), "Fail to response a json string equals retrieve by Id");
    }

    @Test
    @DisplayName("Return Ok and a valid list of pets response")
    void canRetrieveListPets() throws Exception {

        PetResponseDto petResponseDto = this.petResponseBuild();
        List<PetResponseDto> petResponseDtoList = List.of(petResponseDto);

        // given
        given(petServicePort.listPets(anyInt()))
                .willReturn(List.of(
                        this.petDomainBuild()
                ));

        // when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/pets" + "?page=1")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertEquals(HttpStatus.OK.value(), response.getStatus(), "Fail to response a OK status");
        assertEquals(jsonPetResponseList.write(petResponseDtoList).getJson(), response.getContentAsString(), "Fail to response a json string equals retrieve petResponse list");
    }

    //post
    @Test
    @DisplayName("Return CREATED for Post to create a Pet")
    void canRetrievePetWhenCreateWithSuccess() throws Exception {

        PetResponseDto petResponseDto = this.petResponseBuild();

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonPost = objectMapper.writeValueAsString(
                PetRequestDto.builder()
                        .name("Pet 1")
                        .breed("Breed A")
                        .foodRequestDtoSet(
                                Collections.singleton(
                                        FoodRequestDto.builder()
                                                .name("Food 1")
                                                .build()
                                )
                        )
                        .build()
        );

        // given
        given(petServicePort.createPet(any(Pet.class)))
                .willReturn(this.petDomainBuild());

        // when
        MockHttpServletResponse response = mockMvc.perform(
                        post("/pets")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPost))
                .andReturn().getResponse();

        // then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus(), "Fail to response a CREATED status");
        assertEquals(jsonPetResponse.write(petResponseDto).getJson(), response.getContentAsString(), "Fail to response a json string equals when create a Pet");
    }

    // TODO: add test for others methods
    // TODO: add test for exceptions handlers
    // TODO: add test for unmapped exceptions

    private PetResponseDto petResponseBuild() {
        return PetResponseDto.builder()
                .id(petId)
                .name("Pet 1")
                .breed("Breed A")
                .foodResponseDtoSet(Collections.singleton(
                        FoodResponseDto.builder()
                                .id(foodId)
                                .name("Food 1")
                                .build()
                ))
                .build();
    }

    private Pet petDomainBuild() {
        return Pet.builder()
                .id(petId)
                .name("Pet 1")
                .breed("Breed A")
                .foodSet(Collections.singleton(
                        Food.builder()
                                .id(foodId)
                                .name("Food 1")
                                .build()))
                .build();
    }
}