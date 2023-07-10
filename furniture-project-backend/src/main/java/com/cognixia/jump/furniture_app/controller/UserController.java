package com.cognixia.jump.furniture_app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.furniture_app.exception.ResourceNotFoundException;
import com.cognixia.jump.furniture_app.model.User;
import com.cognixia.jump.furniture_app.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) throws ResourceNotFoundException {

        Optional<User> user = userRepo.findById(id);
        
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User", id);
        } else {
            return ResponseEntity.status(200).body(user.get());
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        
        user.setId(null);
        user.setPassword(encoder.encode(user.getPassword()));

        User createdUser = userRepo.save(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@Valid @PathVariable int id, @RequestBody User user) throws ResourceNotFoundException {

        Optional<User> existingUser = userRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new ResourceNotFoundException("User", id);
        } else {
            userRepo.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id) throws ResourceNotFoundException {
        
        Optional<User> existingUser = userRepo.findById(id);
        if (existingUser.isEmpty()) {
            throw new ResourceNotFoundException("User", id);
        } else {
            userRepo.delete(existingUser.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
}
