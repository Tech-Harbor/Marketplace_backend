package com.example.backend.web.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO getByIdUser(Long id);
    UserEntity getById(Long id);
    Optional<UserEntity> getByEmail(String email);
    List<UserDTO> getByAllUser();
    UserDTO updateByIdUser(Long id, UserDTO user);
    void deleteByIdUser(Long id);
}