package com.example.backend.User;

import com.example.backend.Product.ProductDTO;
import com.example.backend.Product.ProductFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final ProductFactory productFactory;

    public UserDTO makeUserFactory(UserEntity user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .number(user.getNumber())
                .product(isNull(user))
                .role(user.getRole())
                .build();
    }

    private List<ProductDTO> isNull(UserEntity user) {
        return Optional.ofNullable(user.getProduct())
                .map(todoList -> todoList.stream()
                        .map(productFactory::makeProduct)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}