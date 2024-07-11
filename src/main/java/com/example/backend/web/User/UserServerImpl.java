package com.example.backend.web.User;

import com.example.backend.utils.general.Helpers;
import com.example.backend.web.File.ImageServer;
import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.*;
import com.example.backend.web.User.store.factory.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.backend.utils.exception.RequestException.badRequestException;
import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServerImpl implements UserServer {

    private final UserImageUpdateInfoFactory userImageUpdateInfoFactory;
    private final UserUpdateInfoFactory userUpdateInfoFactory;
    private final UserSecurityFactory userSecurityFactory;
    private final UserInfoFactory userInfoFactory;
    private final UserRepository userRepository;
    private final ImageServer imageServer;
    private final UserFactory userFactory;
    private final Helpers helpers;

    @Override
    public UserDTO getByIdUser(final Long id) {
        UserEntity userId = getById(id);
        return userFactory.apply(userId);
    }

    @Override
    public UserEntity getById(final Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public Optional<UserEntity> getByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserSecurityDTO> getBySecurityEmail(final String email) {
        final var user = userRepository.findByEmail(email).orElseThrow(
                () -> badRequestException("Not user")
        );

        return ofNullable(userSecurityFactory.apply(user));
    }

    @Override
    public UserEntity getByUserData(final String userData) {
        return userRepository.findByEmail(userData).orElseThrow(
                () -> badRequestException("Not userData: " + userData)
        );
    }

    @Override
    public List<UserDTO> getByAllUser() {
        return userRepository.findAll().stream()
                .map(userFactory)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserUpdateInfoDTO updateByUser(final String jwt, final UserUpdateInfoDTO user) {
        final var byUserData = helpers.tokenUserData(jwt);

        if (StringUtils.isNoneEmpty(user.firstname())) {
            byUserData.setFirstname(user.firstname());
        }

        if (StringUtils.isNoneEmpty(user.lastname())) {
            byUserData.setLastname(user.lastname());
        }

        if (StringUtils.isNoneEmpty(user.phone())) {
            byUserData.setPhone(user.phone());
        }

        if (StringUtils.isNoneEmpty(user.email())) {
            byUserData.setEmail(user.email());
        }

        if (StringUtils.isNoneEmpty(user.password())) {
            byUserData.setPassword(user.password());
        }

        return userUpdateInfoFactory.apply(userRepository.save(byUserData));
    }

    @Override
    @Transactional
    public void deleteUser(final String jwt) {
        final var byUserData = helpers.tokenUserData(jwt);

        userRepository.delete(byUserData);
    }

    @Override
    public UserEntity getByUserFirstName(final String firstName) {
        return userRepository.getByFirstname(firstName).orElseThrow(
                () -> badRequestException("Not user")
        );
    }

    @Override
    public UserSecurityDTO mySecuritySave(final UserEntity user) {
        return userSecurityFactory.apply(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserImageUpdateInfoDTO updateImageUser(final String jwt, final MultipartFile image) {
        final var userData = helpers.tokenUserData(jwt);
        final var uploadImage = imageServer.uploadImageEntity(image);

        userData.setImage(uploadImage);

        userRepository.save(userData);

        return userImageUpdateInfoFactory.apply(userData);
    }

    @Override
    public UserInfoDTO profileUser(final String accessToken) {
        final var user = helpers.tokenUserData(accessToken);

        log.info("Info {}", user);

        return userInfoFactory.apply(user);
    }
}