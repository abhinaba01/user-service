package com.ecommerce.userservice.controller;


import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController{


    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        
        return userService.findUserById(id);

    }

    @GetMapping
    public List<User> getAllUsers(){

        return userService.findAllUsers();
    }

    @PostMapping
    public User saveUser(@RequestBody User user){

        return userService.registerUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id){

        userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id , @RequestBody User user){

        user.setId(id);
        return userService.updateUser(user);
    }

}