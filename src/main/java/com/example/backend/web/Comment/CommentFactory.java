package com.example.backend.web.Comment;

import org.springframework.stereotype.Component;

@Component
public class CommentFactory {
    public CommentDTO makeCommentDTO(final CommentEntity comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .user(comment.getUser())
                .text(comment.getText())
                .product(comment.getProduct())
                .build();
    }
}
