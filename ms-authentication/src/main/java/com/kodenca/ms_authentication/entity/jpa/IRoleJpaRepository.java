package com.kodenca.ms_authentication.entity.jpa;

import com.kodenca.ms_authentication.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleJpaRepository extends JpaRepository<RoleEntity, String> {
    Optional<RoleEntity> findByName(final String name);
}
