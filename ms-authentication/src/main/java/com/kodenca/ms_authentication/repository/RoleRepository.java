package com.kodenca.ms_authentication.repository;

import com.kodenca.ms_authentication.entity.RoleEntity;
import com.kodenca.ms_authentication.entity.jpa.IRoleJpaRepository;
import com.kodenca.ms_authentication.repository.contract.IRoleRepository;
import com.kodenca.ms_authentication.util.RoleEnum;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepository implements IRoleRepository {
    private final IRoleJpaRepository roleJpaRepository;

    public RoleRepository(final IRoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Optional<RoleEntity> getRoleName(RoleEnum name) {
        return roleJpaRepository.findByName(name);
    }
}
