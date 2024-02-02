package com.example.backend.web.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    private static final String CREATE_COMMENT = "/comment";
    private static final String COMMENTS = "/comments";
    private static final String DELETE_COMMENT_ID = "/delete/comment/{id}";

    @PostMapping(CREATE_COMMENT)
    public CommentDTO createComment(@RequestBody CommentEntity comment){
        return commentService.createComment(comment);
    }

    @GetMapping(COMMENTS)
    public List<CommentDTO> getAllComments(){
        return commentService.getAllComments();
    }

    @DeleteMapping(DELETE_COMMENT_ID)
    public String deleteByIdComment(@PathVariable Long id){
        commentService.deleteById(id);
        return "Видалений коміт";
    }
}
