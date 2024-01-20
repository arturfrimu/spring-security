package com.gourav.restapi.models;


import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PetsTest {

    ObjectId id = ObjectId.get();
    Pets pets = new Pets();

    @Test
    void testSetId() {
        pets.setId(id);
        assertEquals(pets.getId(), id);
    }

    @Test
    void testSetName() {
        pets.setName("Liam");
        assertEquals("Liam", pets.getName());
    }

    @Test
    void testSetSpecies() {
        pets.setSpecies("cat");
        assertEquals("cat", pets.getSpecies());
    }

    @Test
    void testSetBreed() {
        pets.setBreed("tabby");
        assertEquals("tabby", pets.getBreed());
    }
}
