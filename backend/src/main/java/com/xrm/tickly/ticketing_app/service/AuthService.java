package com.xrm.tickly.ticketing_app.service;

import com.xrm.tickly.ticketing_app.dto.AuthRequestDTO;
import com.xrm.tickly.ticketing_app.dto.AuthResponseDTO;
import com.xrm.tickly.ticketing_app.dto.RegisterRequestDTO;
import com.xrm.tickly.ticketing_app.dto.UserDTO;
import com.xrm.tickly.ticketing_app.enums.UserRole;
import com.xrm.tickly.ticketing_app.model.User;
import com.xrm.tickly.ticketing_app.repository.UserRepository;
import com.xrm.tickly.ticketing_app.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthResponseDTO login(AuthRequestDTO request) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Fetch the user from the database
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT token
        String token = jwtTokenUtil.generateToken(user);

        // Create UserDTO from User entity
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setAddress(user.getAddress());
        userDTO.setRole(user.getRole());

        // Return AuthResponseDTO
        return new AuthResponseDTO(token, userDTO);
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        // Check if the username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check if the email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create a new User entity
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.USER); // Default role

        // Set additional fields from RegisterRequestDTO
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Generate JWT token for the new user
        String token = jwtTokenUtil.generateToken(savedUser);

        // Create UserDTO from saved User entity
        UserDTO userDTO = new UserDTO();
        userDTO.setId(savedUser.getId());
        userDTO.setUsername(savedUser.getUsername());
        userDTO.setEmail(savedUser.getEmail());
        userDTO.setFirstName(savedUser.getFirstName());
        userDTO.setLastName(savedUser.getLastName());
        userDTO.setPhoneNumber(savedUser.getPhoneNumber());
        userDTO.setAddress(savedUser.getAddress());
        userDTO.setRole(savedUser.getRole());

        // Return AuthResponseDTO
        return new AuthResponseDTO(token, userDTO);
    }
}