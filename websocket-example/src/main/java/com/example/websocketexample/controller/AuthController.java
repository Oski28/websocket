package com.example.websocketexample.controller;

import com.example.websocketexample.config.jwt.JwtUtils;
import com.example.websocketexample.config.services.UserDetailsImplementation;
import com.example.websocketexample.exceptions.TokenException;
import com.example.websocketexample.model.RefreshToken;
import com.example.websocketexample.payload.request.LoginRequest;
import com.example.websocketexample.payload.request.LogoutRequest;
import com.example.websocketexample.payload.request.TokenRefreshRequest;
import com.example.websocketexample.payload.response.JwtResponse;
import com.example.websocketexample.payload.response.MessageResponse;
import com.example.websocketexample.payload.response.TokenRefreshResponse;
import com.example.websocketexample.service.RefreshTokenServiceImplementation;
import com.example.websocketexample.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImplementation userService;
    private final JwtUtils jwtUtils;
    private final RefreshTokenServiceImplementation refreshTokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                          UserServiceImplementation userService, RefreshTokenServiceImplementation refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), roles));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest refreshRequest) {
        String reguestRefreshToken = refreshRequest.getRefreshToken();

        return refreshTokenService.findByToken(reguestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, reguestRefreshToken));
                })
                .orElseThrow(() -> new TokenException(reguestRefreshToken,
                        "Refresh token wygasł."));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid LogoutRequest logoutRequest) {
        refreshTokenService.deleteByUserId(logoutRequest.getUserId());
        return ResponseEntity.ok(new MessageResponse("Użytkownik poprawnie wylogowany."));
    }

}
