package com.example.backend.web.User;

import com.example.backend.security.service.JwtService;
import com.example.backend.web.File.ImageService;
import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserDTO;
import com.example.backend.web.User.store.dto.UserInfoDTO;
import com.example.backend.web.User.store.dto.UserSecurityDTO;
import com.example.backend.web.User.store.factory.UserFactory;
import com.example.backend.web.User.store.factory.UserInfoFactory;
import com.example.backend.web.User.store.factory.UserSecurityFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.backend.utils.exception.RequestException.badRequestException;
import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserSecurityFactory userSecurityFactory;
    private final UserInfoFactory userInfoFactory;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserFactory userFactory;
    private final JwtService jwtService;

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
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
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
    public UserDTO updateByIdUser(final Long id, final UserDTO user) {
        final UserEntity userId = getById(id);

            userId.setFirstname(user.firstname());
            userId.setLastname(user.lastname());
            userId.setPhone(user.phone());
            userId.setEmail(user.email());
            userId.setPassword(user.password());

        return userFactory.apply(userRepository.save(userId));
    }

    @Override
    public void deleteByIdUser(final Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserSecurityDTO mySecuritySave(final UserEntity user) {
        return userSecurityFactory.apply(userRepository.save(user));
    }

    @Override
    public UserInfoDTO updateImageUser(final String jwt, final MultipartFile image) {
        final var token = jwtService.extractUserData(jwt).substring(7);
        final var update = imageService.uploadImageEntity(image);
        final var userData = getByUserData(token);

        userData.setImage(update);

        userRepository.save(userData);

        return userInfoFactory.apply(userData);
    }
}