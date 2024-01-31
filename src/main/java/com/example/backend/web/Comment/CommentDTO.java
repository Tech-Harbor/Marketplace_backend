package com.example.backend.web.Comment;

import com.example.backend.web.Product.ProductEntity;
import com.example.backend.web.User.UserEntity;
import lombok.Builder;

@Builder
public record CommentDTO(Long id, UserEntity user, ProductEntity product, String text) {}
