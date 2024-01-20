package com.gourav.restapi.controllers;

import com.gourav.restapi.controllers.PetsController.PetsResponse;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PetsController.class)
class PetsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetsController petsController;

    @Test
    void getAllPets() throws Exception {
        ObjectId id = ObjectId.get();
        PetsResponse pets = new PetsResponse(id.toHexString(), "Liam", "cat", "tabby");

        List<PetsResponse> allPets = singletonList(pets);

        given(petsController.getAllPets()).willReturn(allPets);

        mvc.perform(get("/pets/").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Liam"))
                .andExpect(jsonPath("$[0].breed").value("tabby"))
                .andExpect(jsonPath("$[0].species").value("cat"))
                .andReturn();
    }

    @Test
    void getPetById() throws Exception {
        ObjectId id = ObjectId.get();
        PetsResponse pets = new PetsResponse(id.toHexString(), "Liam", "cat", "tabby");

        given(petsController.getPetById(id)).willReturn(pets);

        mvc.perform(get("/pets/" + id + "/").contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Liam"))
                .andExpect(jsonPath("$.breed").value("tabby"))
                .andExpect(jsonPath("$.species").value("cat"))
                .andReturn();
    }
}
