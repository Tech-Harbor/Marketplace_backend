package com.example.backend.web.User;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private static final String URI_USERS_ID = "/{id}";

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getByAllUser();
    }

    @PutMapping(URI_USERS_ID)
    public UserDTO updateByIdUser(@PathVariable Long id, @RequestBody UserDTO user){
        return userService.updateByIdUser(id, user);
    }

    @GetMapping(URI_USERS_ID)
    public UserDTO getById(@PathVariable Long id){
        return userService.getByIdUser(id);
    }

    @DeleteMapping(URI_USERS_ID)
    public String deleteId(@PathVariable Long id){
        userService.deleteByIdUser(id);
        return "Видалений користувач" + id;
    }
}
