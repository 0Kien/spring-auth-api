package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return "Username already exists";
        }

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashed);
        userRepository.save(user);

        return "Registered successfully!";
    }

    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) return false;

        return BCrypt.checkpw(password, user.getPassword());
    }
}
