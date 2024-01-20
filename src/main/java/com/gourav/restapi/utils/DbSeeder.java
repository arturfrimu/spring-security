package com.gourav.restapi.utils;

import com.gourav.restapi.models.ERole;
import com.gourav.restapi.models.Pets;
import com.gourav.restapi.models.Role;
import com.gourav.restapi.repositories.PetsRepository;
import com.gourav.restapi.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DbSeeder {

    private final PetsRepository petsRepository;
    private final RoleRepository roleRepository;

    @EventListener
    public void savePets(ContextRefreshedEvent event) {
        petsRepository.deleteAll();

        List<Pets> petsList = Arrays.asList(
                new Pets("Spike", "Dog", "Bulldog"),
                new Pets("Tom", "Cat", "Regular"),
                new Pets("Jerry", "Mouse", "special")
        );

        petsRepository.saveAll(petsList);
    }

    @EventListener
    public void saveRoles(ContextRefreshedEvent event) {
        roleRepository.deleteAll();

        List<Role> roles = Arrays.asList(
                new Role(ERole.ROLE_ADMIN),
                new Role(ERole.ROLE_USER),
                new Role(ERole.ROLE_MODERATOR)
        );

        roleRepository.saveAll(roles);
    }
}
