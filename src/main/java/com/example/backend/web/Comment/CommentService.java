package com.example.backend.web.Comment;

import java.util.List;

public interface CommentService {
    CommentDTO createComment(CommentDTO comment);
    void deleteById(Long id);
    List<CommentDTO> getAllComments();
}
