package com.webbfontaine.task.githubanalytics.api;

import com.webbfontaine.task.githubanalytics.security.AuthRequest;
import com.webbfontaine.task.githubanalytics.security.AuthResponse;
import com.webbfontaine.task.githubanalytics.security.PBKDF2Encoder;
import com.webbfontaine.task.githubanalytics.security.JWTUtil;
import com.webbfontaine.task.githubanalytics.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class AuthenticationApi {

    private final JWTUtil jwtUtil;

    private final PBKDF2Encoder passwordEncoder;

    private final UserService userService;

    public AuthenticationApi(JWTUtil jwtUtil, PBKDF2Encoder passwordEncoder, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar) {
        return userService
                .findByUsername(ar.getUsername())
                .map((userDetails) -> {
                    if (passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword())) {
                        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails)));
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                    }
                }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
