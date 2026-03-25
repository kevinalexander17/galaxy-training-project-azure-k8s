package com.kodenca.ms_authentication.entity.jpa;

import com.kodenca.ms_authentication.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRoleJpaRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByUserId(final String userId);
}
