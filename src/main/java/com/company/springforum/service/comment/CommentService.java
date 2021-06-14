package com.company.springforum.service.comment;


import com.company.springforum.model.Comments;

public interface CommentService {
    public Comments addComment(String num, int aid, String comments);
}
