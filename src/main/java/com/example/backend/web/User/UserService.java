package com.example.backend.web.User;

import com.example.backend.web.User.store.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO getByIdUser(Long id);
    UserEntity getById(Long id);
    Optional<UserEntity> getByEmail(String email);
    UserEntity getByUserData(String userData);
    List<UserDTO> getByAllUser();
    UserDTO updateByIdUser(Long id, UserDTO user);
    void deleteByIdUser(Long id);
    UserEntity mySave(UserEntity user);
}