package com.example.websocketexample.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private List<String> roles;

    public JwtResponse(String token, String refreshToken, Long id, String username,
                       List<String> roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
