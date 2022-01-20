package com.example.websocketexample.service;

public interface RefreshTokenService {

    void removeExpiredTokens() throws InterruptedException;
}
