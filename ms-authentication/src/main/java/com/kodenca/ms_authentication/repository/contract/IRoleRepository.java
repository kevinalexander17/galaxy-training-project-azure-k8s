package com.kodenca.ms_authentication.repository.contract;

import com.kodenca.ms_authentication.entity.RoleEntity;
import com.kodenca.ms_authentication.util.RoleEnum;

import java.util.Optional;

public interface IRoleRepository {

    Optional<RoleEntity> getRoleName(final RoleEnum name);
}
