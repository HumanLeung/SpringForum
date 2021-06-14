package com.company.springforum.service.comment;

import com.company.springforum.model.Articles;
import com.company.springforum.model.Comments;
import com.company.springforum.model.User;
import com.company.springforum.repository.ArticleRepository;
import com.company.springforum.repository.CommentRepository;
import com.company.springforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;

  @Autowired
  public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository
  , ArticleRepository articleRepository) {
      this.commentRepository = commentRepository;
      this.userRepository = userRepository;
      this.articleRepository = articleRepository;
  }
    @Override
     public Comments addComment(String num, int aid, String comments) {
     User user = userRepository.findUserByUserNum(num);
     Comments newComment = new Comments(comments);
     user.addComments(newComment);
     Comments comment = commentRepository.save(newComment);
     Articles article = articleRepository.findByArticleId(aid);
     article.addComments(comment);
     Comments comments1 = commentRepository.save(comment);
     System.out.println(comments1);
     return comments1;
  }
}
