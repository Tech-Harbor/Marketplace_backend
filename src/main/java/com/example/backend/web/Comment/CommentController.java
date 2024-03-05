package com.example.backend.web.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private static final String URI_COMMENTS_ID = "/{id}";

    @PostMapping
    public CommentDTO createComment(@RequestBody CommentDTO comment){
        return commentService.createComment(comment);
    }

    @GetMapping
    public List<CommentDTO> getAllComments(){
        return commentService.getAllComments();
    }

    @DeleteMapping(URI_COMMENTS_ID)
    public String deleteByIdComment(@PathVariable Long id){
        commentService.deleteById(id);
        return "Видалений коміт";
    }
}
