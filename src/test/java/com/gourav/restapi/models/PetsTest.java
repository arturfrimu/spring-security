package com.gourav.restapi.models;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;

public class PetsTest {

    ObjectId id = ObjectId.get();
    Pets pets = new Pets();

    @Test
    public void testSetId() {
        pets.setId(id);
        assertEquals(pets.getId(), id);
    }

    @Test
    public void testSetName() {
        pets.setName("Liam");
        assertEquals("Liam", pets.getName());
    }

    @Test
    public void testSetSpecies() {
        pets.setSpecies("cat");
        assertEquals("cat", pets.getSpecies());
    }

    @Test
    public void testSetBreed() {
        pets.setBreed("tabby");
        assertEquals("tabby", pets.getBreed());
    }
}
