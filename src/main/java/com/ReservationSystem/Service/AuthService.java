package com.ReservationSystem.Service;

import com.ReservationSystem.Dto.AuthResponse;
import com.ReservationSystem.Dto.LoginRequest;
import com.ReservationSystem.Dto.RegisterRequest;
import com.ReservationSystem.Model.Role;
import com.ReservationSystem.Model.User;
import com.ReservationSystem.Repository.UserRepository;
import com.ReservationSystem.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return AuthResponse.builder()
                .token(jwtUtil.generateToken(user.getEmail()))
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER); // siempre USER al registrarse

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtUtil.generateToken(user.getEmail()))
                .build();
    }
}