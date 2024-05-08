package com.example.backend.web.User;


import com.example.backend.web.User.store.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private static final String URI_USERS_ID = "/{id}";

    @QueryMapping
    public List<UserDTO> getAllUsers() {
        return userService.getByAllUser();
    }

    @PatchMapping(URI_USERS_ID)
    @MutationMapping
    public UserDTO updateByIdUser(@PathVariable @Argument final Long id,
                                  @RequestBody @Argument final UserDTO user) {
        return userService.updateByIdUser(id, user);
    }

    @QueryMapping
    public UserDTO getByIdUser(@Argument final Long id) {
        return userService.getByIdUser(id);
    }

    @DeleteMapping(URI_USERS_ID)
    public String deleteId(@PathVariable final Long id) {
        userService.deleteByIdUser(id);
        return "Видалений користувач" + id;
    }
}
