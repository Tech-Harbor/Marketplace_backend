package com.example.backend.User;

public interface UserService {
    UserDTO createUser(UserEntity user);
    UserDTO getByIdUser(Long id);
    UserDTO updateByIdUser(Long id, UserEntity user);
    void deleteByIdUser(Long id);
}