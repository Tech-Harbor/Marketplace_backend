package com.example.backend.web.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserEntity user);
    UserDTO getByIdUser(Long id);
    Optional<UserEntity> getByEmail(String email);
    List<UserDTO> getByAllUser();
    UserDTO updateByIdUser(Long id, UserEntity user);
    void deleteByIdUser(Long id);
}