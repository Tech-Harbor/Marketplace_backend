package com.example.backend.User;

import com.example.backend.Product.ProductFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final ProductFactory productFactory;

    public UserDTO makeUserFactory(UserEntity user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .number(user.getNumber())
                .product(user.getProduct().stream().map(productFactory::makeProduct).collect(Collectors.toList()))
                .role(user.getRole())
                .build();
    }
}