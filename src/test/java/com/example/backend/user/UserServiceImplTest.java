package com.example.backend.user;

import com.example.backend.web.User.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public void getByIdUserTest() {
        Long userId = 1L;

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        UserDTO expectedUserDTO = UserDTO.builder()
                .id(userId)
                .build();

        when(userService.getById(userId)).thenReturn(userEntity);
        when(userFactory.makeUserFactory(any(UserEntity.class))).thenReturn(expectedUserDTO);

        UserDTO resultUserDTO = userService.getByIdUser(userId);

        assertEquals(expectedUserDTO, resultUserDTO);
    }

    @Test
    public void getByIdUserNotTest(){
        Long userId = 1L;

        when(userService.getById(userId)).thenReturn(null);
        when(userFactory.makeUserFactory(isNull())).thenReturn(null);

        UserDTO resultUserDTO = userService.getByIdUser(userId);

        assertNull(resultUserDTO);

        verify(userRepository).getReferenceById(userId);
        verify(userFactory, never()).makeUserFactory(any(UserEntity.class));
    }

    @Test
    public void getByIdTest() {
        Long userId = 1L;

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .build();

        when(userService.getById(userId)).thenReturn(userEntity);

        UserEntity resultUserEntity = userService.getById(userId);

        assertEquals(userEntity, resultUserEntity);
    }

    @Test
    public void getByIdNotTest() {
        Long userId = 1L;

        when(userService.getById(userId)).thenReturn(null);

        UserEntity resultUserEntity = userService.getById(userId);

        assertNull(resultUserEntity);

        verify(userRepository).getReferenceById(userId);
    }

    @Test
    public void getByEmailTest(){
        String emailUser = "email";

        UserEntity userEntity = UserEntity.builder()
                .email(emailUser)
                .build();

        when(userService.getByEmail(emailUser)).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> emailUserEntity = userService.getByEmail(emailUser);

        assertEquals(userEntity, emailUserEntity.orElse(null));
    }

    @Test
    public void updateByIdUserTest(){
        Long userId = 1L;

        UserDTO userDTOUpdate = UserDTO.builder()
                .lastname("lastname")
                .firstname("firstname")
                .phone("phone")
                .email("email")
                .password("password")
                .build();

        UserEntity existingUser = UserEntity.builder()
                .id(userId)
                .build();

        when(userService.getById(userId)).thenReturn(existingUser);
        when(userService.mySave(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDTO saveUserDto = UserDTO.builder()
                .lastname("lastname")
                .firstname("firstname")
                .phone("phone")
                .email("email")
                .password("password")
                .build();

        userService.updateByIdUser(userId, saveUserDto);

        assertEquals(userDTOUpdate.lastname(), saveUserDto.lastname());
        assertEquals(userDTOUpdate.firstname(), saveUserDto.firstname());
        assertEquals(userDTOUpdate.phone(), saveUserDto.phone());
        assertEquals(userDTOUpdate.email(), saveUserDto.email());
        assertEquals(userDTOUpdate.password(), saveUserDto.password());
    }

    @Test
    public void getByAllUserTest(){
        UserEntity userEntity1 = UserEntity.builder().build();
        UserEntity userEntity2 = UserEntity.builder().build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(userEntity1, userEntity2));

        UserDTO userDTO1 = UserDTO.builder().build();
        UserDTO userDTO2 = UserDTO.builder().build();

        when(userFactory.makeUserFactory(userEntity1)).thenReturn(userDTO1);
        when(userFactory.makeUserFactory(userEntity2)).thenReturn(userDTO2);

        List<UserDTO> result = userService.getByAllUser();

        assertEquals(2, result.size());
        assertEquals(userDTO1, result.get(0));
        assertEquals(userDTO2, result.get(1));
    }

    @Test
    public void deleteByIdUserTest(){
        Long userDeleteId = 1L;

        userService.deleteByIdUser(userDeleteId);

        verify(userRepository).deleteById(userDeleteId);
    }
}