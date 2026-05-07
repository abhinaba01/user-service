package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.LoginRequestDTO;
import com.ecommerce.userservice.dto.LoginResponseDTO;
import com.ecommerce.userservice.dto.UserRequestDTO;
import com.ecommerce.userservice.dto.UserResponseDTO;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.EmailAlreadyExistsException;
import com.ecommerce.userservice.exception.InvalidCredentialsException;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO registerUser(UserRequestDTO request) {


        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole("USER");


        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("User with this email already exists");
        }


        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser.getId(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );

    }

    public UserResponseDTO findUserById(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID" + id + "not found"));

        return new UserResponseDTO(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream().map(
                user -> new UserResponseDTO(user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole()
                )).toList();

    }

    public void deleteUserById(Long id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User with ID" + id + "not found");
        }

        userRepository.deleteById(id);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {

        User existingUser = userRepository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        existingUser.setEmail(request.getEmail());

        User newUser = userRepository.save(existingUser);

        return new UserResponseDTO(newUser.getId(),
                newUser.getFirstName(),
                newUser.getLastName(),
                newUser.getEmail(),
                newUser.getRole()
        );

    }



}
