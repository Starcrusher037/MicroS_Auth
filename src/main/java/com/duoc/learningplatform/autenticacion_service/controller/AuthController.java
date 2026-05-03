package com.duoc.learningplatform.autenticacion_service.controller;

import com.duoc.learningplatform.autenticacion_service.dto.LoginRequest;
import com.duoc.learningplatform.autenticacion_service.dto.LoginResponse;
import com.duoc.learningplatform.autenticacion_service.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        // valida usuario y password contra BD
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getContrasenia()
                )
        );

        // carga usuario desde BD
        UserDetails user = userDetailsService.loadUserByUsername(request.getCorreo());

        // genera token
        String token = jwtUtil.generateToken(user);

        return new LoginResponse(token);
    }
}