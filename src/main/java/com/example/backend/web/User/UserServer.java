package com.example.backend.web.User;

import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserServer {
    UserDTO getByIdUser(Long id);
    UserEntity getById(Long id);
    Optional<UserEntity> getByEmail(String email);
    Optional<UserSecurityDTO> getBySecurityEmail(String email);
    List<UserDTO> getByAllUser();
    UserUpdateInfoDTO updateByUser(String jwt, UserUpdateInfoDTO user);
    void deleteUser(String jwt);
    UserEntity getByUserFirstName(String firstName);
    UserSecurityDTO mySecuritySave(UserEntity user);
    UserImageUpdateInfoDTO updateImageUser(String jwt, MultipartFile image);
    /**
     * Retrieves user information based on a JWT token.
     *
     * @param accessToken The JWT token used for identifying and extracting user data
     * @return UserInfoDTO containing the user's information
     * @throws RuntimeException if a user with the extracted data is not found
     */
    UserInfoDTO profileUser(String accessToken);
}