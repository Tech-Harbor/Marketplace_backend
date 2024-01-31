package com.example.backend.User;

import com.example.backend.Product.ProductDTO;
import com.example.backend.utils.enums.Role;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDTO(Long id,
                      String lastname,
                      String firstname,
                      String email,
                      String number,
                      String password,
//                      List<OrderEntity> orderEntity,
//                      List<CommentEntity> comments,
                      List<ProductDTO> product,
                      Role role) {}
