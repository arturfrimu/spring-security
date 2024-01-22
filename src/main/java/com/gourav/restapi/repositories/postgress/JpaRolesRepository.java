package com.gourav.restapi.repositories.postgress;

import com.gourav.restapi.models.postgress.ERole;
import com.gourav.restapi.models.postgress.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRolesRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERole eRole);
}
