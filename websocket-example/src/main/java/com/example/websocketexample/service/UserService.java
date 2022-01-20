package com.example.websocketexample.service;

import com.example.websocketexample.model.User;

public interface UserService {

    User findByUsername(String username);
}
