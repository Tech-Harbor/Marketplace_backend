package com.example.backend.web.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO getByIdUser(final Long id);
    UserEntity getById(final Long id);
    Optional<UserEntity> getByEmail(final String email);
    List<UserDTO> getByAllUser();
    UserDTO updateByIdUser(final Long id, final UserDTO user);
    void deleteByIdUser(final Long id);
    UserEntity mySave(final UserEntity user);
}