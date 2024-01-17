package com.gourav.restapi.controllers;

import com.gourav.restapi.models.Pets;
import com.gourav.restapi.repositories.PetsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetsController {

	private final PetsRepository repository;

	@GetMapping(value = "/")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public List<Pets> getAllPets() {
		return repository.findAll();
	}

	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Pets getPetById(@PathVariable("id") ObjectId id) {
		return repository.findById(id);
	}

	@PutMapping(value = "/{id}")
	@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
	public void modifyPetById(@PathVariable("id") ObjectId id, @Valid @RequestBody Pets pets) {
		pets.setId(id);
		repository.save(pets);
	}

	@PostMapping(value = "/")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public Pets createPet(@Valid @RequestBody Pets pets) {
		pets.setId(ObjectId.get());
		repository.save(pets);
		return pets;
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deletePet(@PathVariable ObjectId id) {
		repository.delete(repository.findById(id));
	}
}
