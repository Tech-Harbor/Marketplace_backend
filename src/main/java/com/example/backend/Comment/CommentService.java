package com.example.backend.Comment;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentEntity comment);
    void deleteById(Long id);

    List<CommentDTO> getAllComments();
}
