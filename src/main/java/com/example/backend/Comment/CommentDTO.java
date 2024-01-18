package com.example.backend.Comment;

import com.example.backend.Product.ProductEntity;
import com.example.backend.User.UserEntity;
import lombok.Builder;

@Builder
public record CommentDTO(Long id, UserEntity user, ProductEntity product, String text) {}
