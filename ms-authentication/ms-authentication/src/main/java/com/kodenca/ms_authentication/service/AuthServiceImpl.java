package com.kodenca.ms_authentication.service;

import com.kodenca.ms_authentication.dto.request.LoginRequest;
import com.kodenca.ms_authentication.dto.request.RegisterRequest;
import com.kodenca.ms_authentication.dto.response.AuthResponse;
import com.kodenca.ms_authentication.entity.UserEntity;
import com.kodenca.ms_authentication.exception.UserAlreadyExistsException;
import com.kodenca.ms_authentication.exception.UserNotFoundException;
import com.kodenca.ms_authentication.mapper.IUserMapper;
import com.kodenca.ms_authentication.repository.contract.IUserRepository;
import com.kodenca.ms_authentication.service.contract.IAuthService;
import com.kodenca.ms_authentication.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
    
    private final IUserRepository userRepository;
    private final IUserMapper IUserMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(IUserRepository userRepository, 
                          IUserMapper IUserMapper,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.IUserMapper = IUserMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public AuthResponse register(final RegisterRequest registerRequest) {
        if (userRepository.getUserByUserName(registerRequest.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException(registerRequest.getUserName());
        }

        UserEntity userEntity = IUserMapper.toEntity(registerRequest);

        userEntity.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        
        // Guardar usuario
        UserEntity savedUser = userRepository.createNewUser(userEntity);
        
        // Generar token
        String token = jwtUtil.generateToken(savedUser.getUserName(), "USER");
        
        return new AuthResponse(token);
    }
    
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // Buscar usuario
        UserEntity user = userRepository.getUserByUserName(loginRequest.getUserName())
                .orElseThrow(() -> new UserNotFoundException("User not found: " + loginRequest.getUserName()));
        
        // Verificar password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid password");
        }
        
        // Generar token
        String token = jwtUtil.generateToken(user.getUserUuId(), "USER");
        
        return new AuthResponse(token);
    }
}
