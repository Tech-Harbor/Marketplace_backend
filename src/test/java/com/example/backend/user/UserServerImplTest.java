package com.example.backend.user;

import com.example.backend.utils.general.Helpers;
import com.example.backend.web.User.UserRepository;
import com.example.backend.web.User.UserServerImpl;
import com.example.backend.web.User.store.UserEntity;
import com.example.backend.web.User.store.dto.UserDTO;
import com.example.backend.web.User.store.dto.UserUpdateInfoDTO;
import com.example.backend.web.User.store.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.example.backend.utils.general.Constants.EMAIL_KEY;
import static com.example.backend.utils.general.Constants.PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServerImplTest {

    @InjectMocks
    private UserServerImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private Helpers helpers;

    @Test
    void getByIdUserTest() {
        final var userId = 1L;

        final var userEntity = UserEntity.builder()
                .id(userId)
                .build();

        final var expectedUserDTO = UserDTO.builder()
                .id(userId)
                .build();

        when(userService.getById(userId)).thenReturn(userEntity);
        when(userMapper.userMapperDTO(any(UserEntity.class))).thenReturn(expectedUserDTO);

        final var resultUserDTO = userService.getByIdUser(userId);

        assertEquals(expectedUserDTO, resultUserDTO);
    }

    @Test
    void getByIdUserNotTest() {
        final var userId = 1L;

        when(userService.getById(userId)).thenReturn(null);
        when(userMapper.userMapperDTO(isNull())).thenReturn(null);

        final var resultUserDTO = userService.getByIdUser(userId);

        assertNull(resultUserDTO);

        verify(userRepository).getReferenceById(userId);
        verify(userMapper, never()).userMapperDTO(any(UserEntity.class));
    }

    @Test
    void getByIdTest() {
        final var userId = 1L;

        final var userEntity = UserEntity.builder()
                .id(userId)
                .build();

        when(userService.getById(userId)).thenReturn(userEntity);

        final var resultUserEntity = userService.getById(userId);

        assertEquals(userEntity, resultUserEntity);
    }

    @Test
    void getByIdNotTest() {
        final var userId = 1L;

        when(userService.getById(userId)).thenReturn(null);

        UserEntity resultUserEntity = userService.getById(userId);

        assertNull(resultUserEntity);

        verify(userRepository).getReferenceById(userId);
    }

    @Test
    void getByEmailTest() {
        final var userEntity = UserEntity.builder()
                .email(EMAIL_KEY)
                .build();

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.of(userEntity));

        final var emailUserEntity = userService.getByEmail(EMAIL_KEY);

        assertEquals(userEntity, emailUserEntity.orElse(null));
    }

    @Test
    void updateByUserTest() {
        final String jwt = "Bearer sample.jwt.token";

        final var userDTOUpdate = UserUpdateInfoDTO.builder()
                .lastname("lastname")
                .firstname("firstname")
                .phone("phone")
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final var user = UserEntity.builder()
                .lastname("lastname")
                .firstname("firstname")
                .phone("phone")
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        when(helpers.tokenUserData(anyString())).thenReturn(user);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        when(userMapper.userMapperUpdateInfoDTO(any(UserEntity.class))).thenReturn(userDTOUpdate);

        final var updatedUser = userService.updateByUser(jwt, userDTOUpdate);

        assertEquals(userDTOUpdate.firstname(), updatedUser.firstname());
        assertEquals(userDTOUpdate.lastname(), updatedUser.lastname());
        assertEquals(userDTOUpdate.phone(), updatedUser.phone());
        assertEquals(userDTOUpdate.email(), updatedUser.email());
        assertEquals(userDTOUpdate.password(), updatedUser.password());

        verify(helpers).tokenUserData(jwt);
        verify(userRepository).save(Objects.requireNonNull(user));
        verify(userMapper).userMapperUpdateInfoDTO(user);
    }

    @Test
    void getByAllUserTest() {
        final var userEntity1 = UserEntity.builder().build();
        final var userEntity2 = UserEntity.builder().build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(userEntity1, userEntity2));

        final var userDTO1 = UserDTO.builder().build();
        final var userDTO2 = UserDTO.builder().build();

        when(userMapper.userMapperDTO(userEntity1)).thenReturn(userDTO1);
        when(userMapper.userMapperDTO(userEntity2)).thenReturn(userDTO2);

        final var result = userService.getByAllUser();

        assertEquals(2, result.size());
        assertEquals(userDTO1, result.get(0));
        assertEquals(userDTO2, result.get(1));
    }

    @Test
    void deleteUserTest() {
        final String jwt = "Bearer sample.jwt.token";

        final var user = UserEntity.builder()
                .lastname("lastname")
                .firstname("firstname")
                .phone("phone")
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        when(helpers.tokenUserData(anyString())).thenReturn(user);

        userService.deleteUser(jwt);

        verify(helpers).tokenUserData(jwt);
        verify(userRepository).delete(Objects.requireNonNull(user));
    }
}