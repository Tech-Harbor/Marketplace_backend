package com.example.backend.web.User;


import com.example.backend.utils.annotations.ApiResponseDelete;
import com.example.backend.utils.annotations.ApiResponseInfoOK;
import com.example.backend.utils.annotations.ApiResponseOK;
import com.example.backend.web.User.store.dto.UserDTO;
import com.example.backend.web.User.store.dto.UserImageUpdateInfoDTO;
import com.example.backend.web.User.store.dto.UserInfoDTO;
import com.example.backend.web.User.store.dto.UserUpdateInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@Tag(name = "UserService")
@RequestMapping("/api")
public class UserController {

    private final UserServer userServer;

    private static final String UPDATE_USER = "/updateUser";
    private static final String UPDATE_IMAGE_USER = "/updateImageUser";
    private static final String PROFILE_USER = "/profile";
    private static final String DELETE_USER = "/user/delete";

    @QueryMapping
    public List<UserDTO> getAllUsers() {
        return userServer.getByAllUser();
    }

    @PatchMapping(UPDATE_USER)
    @Operation(summary = "Update current User")
    @ApiResponseOK
    public UserUpdateInfoDTO updateByUser(@RequestHeader(AUTHORIZATION) final String jwt,
                                          @RequestBody final UserUpdateInfoDTO user) {
        return userServer.updateByUser(jwt, user);
    }

    @QueryMapping
    public UserDTO getByIdUser(@Argument final Long id) {
        return userServer.getByIdUser(id);
    }

    @PutMapping(value = UPDATE_IMAGE_USER, consumes = {MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update Image profile for User")
    @ApiResponseOK
    public UserImageUpdateInfoDTO updateImage(@RequestHeader(AUTHORIZATION) final String jwt,
                                              @RequestPart final MultipartFile image) {
        return userServer.updateImageUser(jwt, image);
    }

    @GetMapping(PROFILE_USER)
    @Operation(summary = "Information about the user who is authorized and logged into the system")
    @ApiResponseInfoOK
    public UserInfoDTO profile(@RequestHeader(AUTHORIZATION) final String jwt) {
        return userServer.profileUser(jwt);
    }

    @DeleteMapping(DELETE_USER)
    @Operation(summary = "Delete current User")
    @ApiResponseDelete
    public void deleteUser(@RequestHeader(AUTHORIZATION) final String jwt) {
        userServer.deleteUser(jwt);
    }
}