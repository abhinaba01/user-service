package com.ecommerce.userservice.service;

import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.exception.EmailAlreadyExistsException;
import com.ecommerce.userservice.exception.UserNotFoundException;
import com.ecommerce.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {


        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("User with this email already exists");
        }


        return userRepository.save(user);
    }

    public User findUserById(Long id){

        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID" + id + "not found"));
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(Long id){

        if( !userRepository.existsById(id)){
            throw new UserNotFoundException("User with ID" + id + "not found");
        }

        userRepository.deleteById(id);
    }

    public User updateUser(User user){

        User existingUser = userRepository.findById(user.getId()).
                                orElseThrow(() -> new UserNotFoundException("User with ID" + user.getId() + "not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());



        return userRepository.save(existingUser);
    }
}