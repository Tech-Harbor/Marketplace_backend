package com.example.backend.web.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final CommentFactory commentFactory;

    @Override
    public CommentDTO createComment(final CommentDTO comment) {

        CommentEntity newComment = CommentEntity.builder()
                .id(comment.id())
                .product(comment.product())
                .user(comment.user())
                .text(comment.text())
                .build();

        return commentFactory.makeCommentDTO(commentRepository.save(newComment));
    }

    @Override
    public void deleteById(final Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentFactory::makeCommentDTO)
                .collect(Collectors.toList());
    }
}
