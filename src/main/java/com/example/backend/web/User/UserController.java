package com.example.backend.web.User;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private static final String USERs = "/users";
    private static final String ID_USER_UPDATE_GET_DELETE = "/user/{id}";
    @GetMapping(USERs)
    public List<UserDTO> getAllUsers(){
        return userService.getByAllUser();
    }

    @PutMapping(ID_USER_UPDATE_GET_DELETE)
    public UserDTO updateByIdUser(@PathVariable Long id, @RequestBody UserEntity user){
        return userService.updateByIdUser(id, user);
    }

    @GetMapping(ID_USER_UPDATE_GET_DELETE)
    public UserDTO getById(@PathVariable Long id){
        return userService.getByIdUser(id);
    }

    @DeleteMapping(ID_USER_UPDATE_GET_DELETE)
    public String deleteId(@PathVariable Long id){
        userService.deleteByIdUser(id);
        return "Видалений користувач" + id;
    }
}
