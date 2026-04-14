package com.johnhelder.inventory.controller;

import com.johnhelder.inventory.dto.LoginRequestDTO;
import com.johnhelder.inventory.dto.LoginResponseDTO;
import com.johnhelder.inventory.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.username());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDTO(
                token,
                userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority()
        ));
    }
}
