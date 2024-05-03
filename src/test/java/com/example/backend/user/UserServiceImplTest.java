package com.example.backend.user;

import com.example.backend.web.User.*;
import com.example.backend.web.User.store.dto.UserDTO;
import com.example.backend.web.User.store.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.backend.utils.general.Constants.EMAIL_KEY;
import static com.example.backend.utils.general.Constants.PASSWORD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserFactory userFactory;

    @Test
    void getByIdUserTest() {
        final Long userId = 1L;

        final UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        final UserDTO expectedUserDTO = UserDTO.builder()
                .id(userId)
                .build();

        when(userService.getById(userId)).thenReturn(userEntity);
        when(userFactory.apply(any(UserEntity.class))).thenReturn(expectedUserDTO);

        final UserDTO resultUserDTO = userService.getByIdUser(userId);

        assertEquals(expectedUserDTO, resultUserDTO);
    }

    @Test
    void getByIdUserNotTest() {
        final Long userId = 1L;

        when(userService.getById(userId)).thenReturn(null);
        when(userFactory.apply(isNull())).thenReturn(null);

        final UserDTO resultUserDTO = userService.getByIdUser(userId);

        assertNull(resultUserDTO);

        verify(userRepository).getReferenceById(userId);
        verify(userFactory, never()).apply(any(UserEntity.class));
    }

    @Test
    void getByIdTest() {
        final Long userId = 1L;

        final UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        when(userService.getById(userId)).thenReturn(userEntity);

        final UserEntity resultUserEntity = userService.getById(userId);

        assertEquals(userEntity, resultUserEntity);
    }

    @Test
    void getByIdNotTest() {
        final Long userId = 1L;

        when(userService.getById(userId)).thenReturn(null);

        UserEntity resultUserEntity = userService.getById(userId);

        assertNull(resultUserEntity);

        verify(userRepository).getReferenceById(userId);
    }

    @Test
    void getByEmailTest() {
        final UserEntity userEntity = UserEntity.builder()
                .email(EMAIL_KEY)
                .build();

        when(userService.getByEmail(EMAIL_KEY)).thenReturn(Optional.of(userEntity));

        final Optional<UserEntity> emailUserEntity = userService.getByEmail(EMAIL_KEY);

        assertEquals(userEntity, emailUserEntity.orElse(null));
    }

    @Test
    void updateByIdUserTest() {
        final Long userId = 1L;

        final UserDTO userDTOUpdate = UserDTO.builder()
                .lastname("lastname")
                .firstname("firstname")
                .phone("phone")
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        final UserEntity existingUser = UserEntity.builder()
                .id(userId)
                .build();

        when(userService.getById(userId)).thenReturn(existingUser);
        when(userService.mySave(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        final UserDTO saveUserDto = UserDTO.builder()
                .lastname("lastname")
                .firstname("firstname")
                .phone("phone")
                .email(EMAIL_KEY)
                .password(PASSWORD)
                .build();

        userService.updateByIdUser(userId, saveUserDto);

        assertEquals(userDTOUpdate.lastname(), saveUserDto.lastname());
        assertEquals(userDTOUpdate.firstname(), saveUserDto.firstname());
        assertEquals(userDTOUpdate.phone(), saveUserDto.phone());
        assertEquals(userDTOUpdate.email(), saveUserDto.email());
        assertEquals(userDTOUpdate.password(), saveUserDto.password());
    }

    @Test
    void getByAllUserTest() {
        final UserEntity userEntity1 = UserEntity.builder().build();
        final UserEntity userEntity2 = UserEntity.builder().build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(userEntity1, userEntity2));

        final UserDTO userDTO1 = UserDTO.builder().build();
        final UserDTO userDTO2 = UserDTO.builder().build();

        when(userFactory.apply(userEntity1)).thenReturn(userDTO1);
        when(userFactory.apply(userEntity2)).thenReturn(userDTO2);

        final List<UserDTO> result = userService.getByAllUser();

        assertEquals(2, result.size());
        assertEquals(userDTO1, result.get(0));
        assertEquals(userDTO2, result.get(1));
    }

    @Test
    void deleteByIdUserTest() {
        final Long userDeleteId = 1L;

        userService.deleteByIdUser(userDeleteId);

        verify(userRepository).deleteById(userDeleteId);
    }
}