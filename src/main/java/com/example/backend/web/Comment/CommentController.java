package com.example.backend.web.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private static final String URI_COMMENTS_ID = "/{id}";

    @PostMapping
    public CommentDTO createComment(@RequestBody final CommentDTO comment) {
        return commentService.createComment(comment);
    }

    @GetMapping
    @QueryMapping
    public List<CommentDTO> getAllComments() {
        return commentService.getAllComments();
    }

    @DeleteMapping(URI_COMMENTS_ID)
    public String deleteByIdComment(@PathVariable final Long id) {
        commentService.deleteById(id);
        return "Видалений коміт";
    }
}
