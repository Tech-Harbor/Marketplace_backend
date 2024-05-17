package com.example.backend.web.User;

import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserDTO;
import com.example.backend.web.User.store.dto.UserInfoDTO;
import com.example.backend.web.User.store.dto.UserSecurityDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO getByIdUser(Long id);
    UserEntity getById(Long id);
    Optional<UserEntity> getByEmail(String email);
    Optional<UserSecurityDTO> getBySecurityEmail(String email);
    UserEntity getByUserData(String userData);
    List<UserDTO> getByAllUser();
    UserDTO updateByIdUser(Long id, UserDTO user);
    void deleteByIdUser(Long id);
    UserSecurityDTO mySecuritySave(UserEntity user);
    UserInfoDTO updateImageUser(String jwt, MultipartFile image);
}