package com.gourav.restapi.controllers;

import com.gourav.restapi.models.Pets;
import com.gourav.restapi.repositories.PetsRepository;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetsController {

    private final PetsRepository repository;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<PetsResponse> getAllPets() {
        return repository.findAll().stream()
                .map(PetsResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public PetsResponse getPetById(@PathVariable("id") ObjectId id) {
        return PetsResponse.from(repository.findById(id));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public void modifyPetById(@PathVariable("id") ObjectId id, @Valid @RequestBody Pets pets) {
        pets.setId(id);
        repository.save(pets);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public PetsResponse createPet(@Valid @RequestBody Pets pets) {
        pets.setId(ObjectId.get());
        repository.save(pets);
        return PetsResponse.from(pets);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePet(@PathVariable ObjectId id) {
        repository.delete(repository.findById(id));
    }

    @Getter
    @Setter
    public static class PetsResponse {
        private final String id;
        private final String name;
        private final String species;
        private final String breed;

        public PetsResponse(String id, String name, String species, String breed) {
            this.id = id;
            this.name = name;
            this.species = species;
            this.breed = breed;
        }

        public static PetsResponse from(Pets pets) {
            return new PetsResponse(pets.getId().toHexString(), pets.getName(), pets.getSpecies(), pets.getBreed());
        }
    }
}
