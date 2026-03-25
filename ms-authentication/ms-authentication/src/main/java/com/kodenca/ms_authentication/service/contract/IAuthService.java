package com.kodenca.ms_authentication.service.contract;

import com.kodenca.ms_authentication.dto.request.LoginRequest;
import com.kodenca.ms_authentication.dto.request.RegisterRequest;
import com.kodenca.ms_authentication.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse register(final RegisterRequest registerRequest);
    AuthResponse login(final LoginRequest loginRequest);
}
