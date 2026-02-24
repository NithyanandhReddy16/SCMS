package com.scms.scms_backend.service;

import com.scms.scms_backend.entity.*;
import com.scms.scms_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public User register(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return repo.save(user);
    }

    public User findByEmail(String email){
        return repo.findByEmail(email).orElseThrow();
    }
}