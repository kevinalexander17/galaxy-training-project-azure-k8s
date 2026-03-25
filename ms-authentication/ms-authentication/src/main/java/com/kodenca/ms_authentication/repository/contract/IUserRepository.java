package com.kodenca.ms_authentication.repository.contract;

import com.kodenca.ms_authentication.entity.UserEntity;

import java.util.Optional;

public interface IUserRepository {
    Optional<UserEntity> getUserByUserName(final String userName);
    UserEntity createNewUser(final UserEntity userEntity);

}
