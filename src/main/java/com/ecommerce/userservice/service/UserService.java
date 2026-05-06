package com.ecommerce.userservice.service;

import com.ecommerce.userservice.entity.User;
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
            throw new RuntimeException("Email already exists");
        }


        return userRepository.save(user);
    }

    public User findUserById(Long id){

        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("ID Does not exist"));
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(Long id){

        if( !userRepository.existsById(id)){
            throw new RuntimeException("User with this ID does not exist.");
        }

        userRepository.deleteById(id);
    }

    public User updateUser(User user){

        User existingUser = userRepository.findById(user.getId()).
                                orElseThrow(() -> new RuntimeException("ID Does not Exist"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());



        return userRepository.save(existingUser);
    }
}