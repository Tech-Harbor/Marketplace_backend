package com.example.backend.Comment;

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
    public CommentDTO createComment(CommentEntity comment) {
        return commentFactory.makeCommentDTO(commentRepository.save(comment));
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(commentFactory::makeCommentDTO)
                .collect(Collectors.toList());
    }
}
