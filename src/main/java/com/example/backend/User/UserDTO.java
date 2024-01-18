package com.example.backend.User;

import com.example.backend.Product.ProductDTO;
import com.example.backend.utils.enums.Role;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDTO(Long id,
                      String lastname,
                      String name,
                      String email,
                      String number,
//                      List<OrderEntity> orderEntity,
//                      List<CommentEntity> comments,
                      List<ProductDTO> product,
                      Role role) {}
