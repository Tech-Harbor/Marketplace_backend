package com.example.backend.security.service;


import com.example.backend.security.models.request.GoogleTokenRequest;
import com.example.backend.web.User.UserEntity;

public interface GoogleService {
    UserEntity googleLogin(GoogleTokenRequest token);
}