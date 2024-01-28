package com.example.backend.Order;

import com.example.backend.Product.ProductEntity;
import com.example.backend.User.UserEntity;
import com.example.backend.utils.enums.Status;
import lombok.Builder;

@Builder
public record OrderDTO(Long id, UserEntity user, ProductEntity product, Status status) {}
