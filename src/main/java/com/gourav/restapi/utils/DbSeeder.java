package com.gourav.restapi.utils;

import com.gourav.restapi.models.postgress.ERole;
import com.gourav.restapi.models.mongo.Pets;
import com.gourav.restapi.models.postgress.RoleEntity;
import com.gourav.restapi.repositories.mongo.PetsRepository;
import com.gourav.restapi.repositories.postgress.JpaRolesRepository;
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
    private final JpaRolesRepository jpaRolesRepository;

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
        jpaRolesRepository.deleteAll();

        List<RoleEntity> roles = Arrays.asList(
                new RoleEntity(ERole.ROLE_ADMIN),
                new RoleEntity(ERole.ROLE_USER),
                new RoleEntity(ERole.ROLE_MODERATOR)
        );

        jpaRolesRepository.saveAll(roles);
    }
}
