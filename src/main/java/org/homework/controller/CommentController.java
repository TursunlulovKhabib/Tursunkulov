package org.homework.controller;

import org.homework.model.ArticleId;
import org.homework.model.Comment;
import org.homework.model.CommentData;
import org.homework.model.CommentId;
import org.homework.exception.BadRequestException;
import org.homework.ArticleRepository;
import org.homework.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class CommentController {
  private static final Logger log = LoggerFactory.getLogger(CommentController.class);
  private final CommentRepository commentRepository;
  private final ArticleRepository articleRepository;
  private final ObjectMapper objectMapper;

  public CommentController(ArticleRepository articleRepository, CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
    this.articleRepository = articleRepository;
    this.objectMapper = new ObjectMapper();
  }

  public Object createComment(Request req, Response res) throws Exception {
    String articleIdStr = req.params(":articleId");
    long articleIdVal;
    try {
      articleIdVal = Long.parseLong(articleIdStr);
    } catch (NumberFormatException e) {
      throw new BadRequestException("Invalid article id");
    }
    ArticleId articleId = new ArticleId(articleIdVal);
    articleRepository.findById(articleId); // Чтобы убедиться, что статья есть

    CommentData commentData = objectMapper.readValue(req.body(), CommentData.class);
    if (commentData.getText() == null || commentData.getText().trim().isEmpty()) {
      throw new BadRequestException("Comment text required");
    }

    CommentId newId = commentRepository.generateId();
    Comment comment = new Comment(newId, articleId, commentData.getText());
    commentRepository.save(comment);

    res.type("application/json");
    res.status(201);
    log.debug("POST /articles/:articleId/comments success");
    return objectMapper.writeValueAsString(comment);
  }

  public Object deleteComment(Request req, Response res) throws Exception {
    String commentIdStr = req.params(":commentId");
    long commentIdVal;
    try {
      commentIdVal = Long.parseLong(commentIdStr);
    } catch (NumberFormatException e) {
      throw new BadRequestException("Invalid comment id");
    }
    CommentId commentId = new CommentId(commentIdVal);
    commentRepository.deleteById(commentId);
    res.status(204);
    log.debug("DELETE /comments/:commentId success");
    return "";
  }
}