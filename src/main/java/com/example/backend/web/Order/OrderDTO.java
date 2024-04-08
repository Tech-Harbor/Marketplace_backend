package com.example.backend.web.Order;

import com.example.backend.web.Product.ProductEntity;
import com.example.backend.web.User.UserEntity;
import com.example.backend.utils.enums.Status;
import lombok.Builder;

@Builder
public record OrderDTO(Long id, UserEntity user, ProductEntity product, Status status) { }
