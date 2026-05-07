package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.LoginRequestDTO;
import com.ecommerce.userservice.dto.LoginResponseDTO;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.InvalidCredentialsException;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO loginUser(LoginRequestDTO request) {


        User user = userRepository.findByEmail(request.getEmail()).
                orElseThrow(() -> new UserNotFoundException("This user does not exist"));

        String raw = request.getPassword();
        String hashed = user.getPassword();


        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        else{
            return new LoginResponseDTO(
                    user.getId(),
                    "AUTHENTICATED",
                    user.getEmail(),
                    user.getRole()
            );
        }
    }
}
