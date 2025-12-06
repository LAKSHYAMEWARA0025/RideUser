package org.example.rideshare.service;

import org.example.rideshare.config.JwtUtil;
import org.example.rideshare.dto.AuthResponse;
import org.example.rideshare.dto.LoginRequest;
import org.example.rideshare.dto.RegisterRequest;
import org.example.rideshare.exception.BadRequestException;
import org.example.rideshare.model.User;
import org.example.rideshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        if (!request.getRole().equals("ROLE_USER") &&
                !request.getRole().equals("ROLE_DRIVER")) {
            throw new BadRequestException("Invalid role: must be ROLE_USER or ROLE_DRIVER");
        }

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole()
        );

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new AuthResponse(token);
    }
}
