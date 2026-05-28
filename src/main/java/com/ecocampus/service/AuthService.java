package com.ecocampus.service;

import com.ecocampus.dto.request.LoginRequest;
import com.ecocampus.dto.request.RegisterRequest;
import com.ecocampus.dto.response.JwtResponse;
import com.ecocampus.dto.response.MessageResponse;
import com.ecocampus.dto.response.UserResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    MessageResponse registerUser(RegisterRequest registerRequest);
    UserResponse getCurrentUser(String email);
}