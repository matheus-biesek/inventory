package com.closing.inventory.controller.rest.controller;

import com.closing.inventory.dto.LoginRequestDTO;
import com.closing.inventory.dto.RegisterRequestDTO;
import com.closing.inventory.dto.ResponseDTO;
import com.closing.inventory.infra.security.TokenService;
import com.closing.inventory.model.user.User;
import com.closing.inventory.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.userRepository.findByUsername(body.username()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional <User> user = this.userRepository.findByUsername(body.username());
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setUsername(body.username());
            this.userRepository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }
}
