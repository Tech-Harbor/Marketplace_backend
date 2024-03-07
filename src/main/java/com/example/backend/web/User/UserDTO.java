package com.example.backend.web.User;

import com.example.backend.web.Product.ProductDTO;
import com.example.backend.web.User.utils.RegisterAuthStatus;
import com.example.backend.web.User.utils.Role;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDTO(Long id,
                      String lastname,
                      String firstname,
                      String email,
                      String phone,
                      String password,
//                      List<OrderEntity> orderEntity,
//                      List<CommentEntity> comments,
                      List<ProductDTO> product,
                      RegisterAuthStatus status,
                      Role role) { }
