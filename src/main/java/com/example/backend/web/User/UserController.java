package com.example.backend.web.User;


import com.example.backend.utils.annotations.ApiResponseOK;
import com.example.backend.web.User.store.dto.UserDTO;
import com.example.backend.web.User.store.dto.UserInfoDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@Tag(name = "UserService")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private static final String UPDATE_IMAGE_USER = "/updateImageUser";
    private static final String URI_USERS_ID = "/{id}";

    @QueryMapping
    public List<UserDTO> getAllUsers() {
        return userService.getByAllUser();
    }

    @PatchMapping(URI_USERS_ID)
    @MutationMapping
    public UserDTO updateByIdUser(@PathVariable @Argument final Long id, @RequestBody @Argument final UserDTO user) {
        return userService.updateByIdUser(id, user);
    }

    @QueryMapping
    public UserDTO getByIdUser(@Argument final Long id) {
        return userService.getByIdUser(id);
    }

    @PutMapping(UPDATE_IMAGE_USER)
    @ApiResponseOK
    public UserInfoDTO updateImage(@RequestHeader(AUTHORIZATION) final String jwt, @RequestParam MultipartFile image) {
        return userService.updateImageUser(jwt, image);
    }

    @DeleteMapping(URI_USERS_ID)
    public String deleteId(@PathVariable final Long id) {
        userService.deleteByIdUser(id);
        return "Видалений користувач" + id;
    }
}
