package com.gourav.restapi.models.mongo;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Pets {

	@Id
    private ObjectId id;
    private String name;
    private String species;
    private String breed;

    public Pets() {}

    public Pets(String name, String species, String breed) {
        this.name = name;
        this.species = species;
        this.breed = breed;
    }
}
