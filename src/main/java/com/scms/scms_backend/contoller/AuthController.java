package com.scms.scms_backend.contoller;

import com.scms.scms_backend.service.UserService;
import com.scms.scms_backend.dto.LoginRequest;
import com.scms.scms_backend.entity.User;
import com.scms.scms_backend.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req){
        User user = service.findByEmail(req.getEmail());

        if(!encoder.matches(req.getPassword(),user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return jwtUtil.generateToken(user.getEmail());
    }
}