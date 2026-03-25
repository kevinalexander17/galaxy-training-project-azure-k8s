package com.kodenca.ms_authentication.service;

import com.kodenca.ms_authentication.dto.request.LoginRequest;
import com.kodenca.ms_authentication.dto.request.RegisterRequest;
import com.kodenca.ms_authentication.dto.response.AuthResponse;
import com.kodenca.ms_authentication.entity.RoleEntity;
import com.kodenca.ms_authentication.entity.UserEntity;
import com.kodenca.ms_authentication.exception.RoleNotFoundException;
import com.kodenca.ms_authentication.exception.UserAlreadyExistsException;
import com.kodenca.ms_authentication.exception.UserNotFoundException;
import com.kodenca.ms_authentication.mapper.IUserMapper;
import com.kodenca.ms_authentication.repository.contract.IRoleRepository;
import com.kodenca.ms_authentication.repository.contract.IUserRepository;
import com.kodenca.ms_authentication.service.contract.IAuthService;
import com.kodenca.ms_authentication.security.JwtUtil;
import com.kodenca.ms_authentication.util.RoleEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.kodenca.ms_authentication.util.Constants.INVALID_PASSWORD;
import static com.kodenca.ms_authentication.util.Constants.ROLE_NOT_FOUND;
import static com.kodenca.ms_authentication.util.Constants.USER_NOT_FOUND;

@Service
public class AuthServiceImpl implements IAuthService {

    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final IUserMapper IUserMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(final IRoleRepository roleRepository,
                           final IUserRepository userRepository,
                           final IUserMapper IUserMapper,
                           final JwtUtil jwtUtil,
                           final PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.IUserMapper = IUserMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse register(final RegisterRequest registerRequest) {
        if (userRepository.findByUserNameWithRole(registerRequest.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException(registerRequest.getUserName());
        }

        UserEntity userEntity = IUserMapper.toEntity(registerRequest);
        userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        //saving role
        RoleEnum roleEnum = RoleEnum.valueOf(registerRequest.getRole());
        Optional<RoleEntity> roleEntity = roleRepository.getRoleName(roleEnum);
        if(roleEntity.isEmpty()){
            throw new RoleNotFoundException(ROLE_NOT_FOUND + registerRequest.getRole());
        }
        userEntity.setRoleEntity(roleEntity.get());
        UserEntity savedUser = userRepository.createNewUser(userEntity);

        String token = jwtUtil.generateToken(savedUser.getUserName(), savedUser.getRoleEntity().getName().toString());

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        UserEntity user = userRepository.findByUserNameWithRole(loginRequest.getUserName())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND + loginRequest.getUserName()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundException(INVALID_PASSWORD);
        }

        String token = jwtUtil.generateToken(user.getUserName(), user.getRoleEntity().getName().toString());

        return new AuthResponse(token);
    }
}
