package com.example.backend.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserEntity user);
    UserDTO getByIdUser(Long id);
    List<UserDTO> getByAllUser();
    UserDTO updateByIdUser(Long id, UserEntity user);
    void deleteByIdUser(Long id);
}