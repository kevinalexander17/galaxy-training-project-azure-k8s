package com.kodenca.ms_authentication.entity.jpa;

import com.kodenca.ms_authentication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserJpaRepository extends JpaRepository<UserEntity, String> {

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roleEntity WHERE u.userName = :userName")
    Optional<UserEntity> findByUserName(@Param("userName") final String userName);
}
