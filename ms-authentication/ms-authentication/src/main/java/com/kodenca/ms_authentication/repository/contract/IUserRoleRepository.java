package com.kodenca.ms_authentication.repository.contract;

import com.kodenca.ms_authentication.entity.UserRoleEntity;

import java.util.List;

public interface IUserRoleRepository {
    List<UserRoleEntity> getUserRolesByUserId(final String userId);
}
