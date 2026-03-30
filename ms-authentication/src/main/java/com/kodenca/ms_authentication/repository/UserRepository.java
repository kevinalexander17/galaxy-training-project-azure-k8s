package com.kodenca.ms_authentication.repository;

import com.kodenca.ms_authentication.entity.UserEntity;
import com.kodenca.ms_authentication.entity.jpa.IUserJpaRepository;
import com.kodenca.ms_authentication.repository.contract.IUserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class UserRepository implements IUserRepository {
    private final IUserJpaRepository iUserJpaRepository;

    public UserRepository(final IUserJpaRepository iUserJpaRepository) {
        this.iUserJpaRepository = iUserJpaRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserEntity> findByUserNameWithRole(final String userName) {
        return iUserJpaRepository.findByUserName(userName);
    }
    
    @Override
    @Transactional
    public UserEntity createNewUser(final UserEntity userEntity) {
        return iUserJpaRepository.save(userEntity);
    }
}
