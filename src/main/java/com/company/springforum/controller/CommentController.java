package com.company.springforum.controller;


import com.company.springforum.dto.CommentDTO;
import com.company.springforum.model.Comments;
import com.company.springforum.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",  allowedHeaders = "*")
@RequestMapping("/api/comment")
public class CommentController {

    public CommentService service;

    @Autowired
    public CommentController(CommentService commentService) {
    this.service = commentService;
    }

    @PostMapping("/add/{aid}")
    public ResponseEntity<String> addComment(@PathVariable int aid, @RequestBody CommentDTO commentDTO) {
        Comments comments = service.addComment(commentDTO.getUserNum(), aid, commentDTO.getComments());
        if (comments != null) {
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("OK", HttpStatus.BAD_REQUEST);
        }
    }
}
