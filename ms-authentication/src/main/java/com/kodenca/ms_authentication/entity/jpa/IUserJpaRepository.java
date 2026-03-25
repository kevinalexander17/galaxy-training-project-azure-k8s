package com.kodenca.ms_authentication.entity.jpa;

import com.kodenca.ms_authentication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserJpaRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserName(final String userName);
}
