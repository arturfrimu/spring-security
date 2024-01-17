package com.gourav.restapi.utils;

import com.gourav.restapi.models.Pets;
import com.gourav.restapi.repositories.PetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbSeeder {

    private final PetsRepository petsRepository;

    @EventListener
    public void savePets() {
        petsRepository.deleteAll();
        List<Pets> petsList = Arrays.asList(new Pets("Spike", "Dog", "Bulldog"),
                new Pets("Tom", "Cat", "Regular"),
                new Pets("Jerry", "Mouse", "special"));

        petsRepository.saveAll(petsList);
    }
}
