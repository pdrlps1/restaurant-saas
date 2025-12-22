package com.restaurant.saas.infrastructure.persistence;

import com.restaurant.saas.domain.entities.Role;
import com.restaurant.saas.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleType name);

    boolean existsByName(RoleType name);
}
