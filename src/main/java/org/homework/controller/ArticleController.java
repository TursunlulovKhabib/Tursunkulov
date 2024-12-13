package org.homework.controller;

import org.homework.exception.BadRequestException;
import org.homework.model.Article;
import org.homework.model.ArticleId;
import org.homework.repository.ArticleRepository;
import org.homework.repository.CommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleController {
  private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  private final ObjectMapper objectMapper;

  public ArticleController(ArticleRepository articleRepository, CommentRepository commentRepository) {
    this.articleRepository = articleRepository;
    this.commentRepository = commentRepository;
    this.objectMapper = new ObjectMapper();
  }

  public Object getAllArticles(Request req, Response res) throws Exception {
    List<Article> articles = articleRepository.findAll().stream()
        .map(a -> new Article(a.getId(), a.getTitle(), a.getTags(), commentRepository.findByArticleId(a.getId())))
        .collect(Collectors.toList());
    res.type("application/json");
    log.debug("GET /articles success");
    return objectMapper.writeValueAsString(articles);
  }

  public Object getArticle(Request req, Response res) throws Exception {
    String idStr = req.params(":id");
    long idVal;
    try {
      idVal = Long.parseLong(idStr);
    } catch (NumberFormatException e) {
      throw new BadRequestException("Invalid article id");
    }
    ArticleId id = new ArticleId(idVal);
    Article a = articleRepository.findById(id);
    a = new Article(a.getId(), a.getTitle(), a.getTags(), commentRepository.findByArticleId(a.getId()));
    res.type("application/json");
    log.debug("GET /articles/:id success");
    return objectMapper.writeValueAsString(a);
  }

  public Object createArticle(Request req, Response res) throws Exception {
    Map<String,Object> body = objectMapper.readValue(req.body(), Map.class);
    String title = (String)body.get("title");
    List<String> tags = (List<String>) body.getOrDefault("tags", Collections.emptyList());
    if (title == null || title.trim().isEmpty()) {
      throw new BadRequestException("Title is required");
    }

    ArticleId newId = articleRepository.generateId();
    Article article = new Article(newId, title, new HashSet<>(tags), Collections.emptyList());
    articleRepository.save(article);
    res.type("application/json");
    res.status(201);
    log.debug("POST /articles success");
    return objectMapper.writeValueAsString(article);
  }

  public Object updateArticle(Request req, Response res) throws Exception {
    String idStr = req.params(":id");
    long idVal;
    try {
      idVal = Long.parseLong(idStr);
    } catch (NumberFormatException e) {
      throw new BadRequestException("Invalid article id");
    }
    ArticleId id = new ArticleId(idVal);
    Map<String,Object> body = objectMapper.readValue(req.body(), Map.class);
    String title = (String)body.get("title");
    if (title == null || title.trim().isEmpty()) {
      throw new BadRequestException("Title is required");
    }

    Article a = articleRepository.findById(id);
    Article updated = a.withTitle(title);
    articleRepository.save(updated);
    res.type("application/json");
    log.debug("PUT /articles/:id success");
    return objectMapper.writeValueAsString(updated);
  }

  public Object deleteArticle(Request req, Response res) throws Exception {
    String idStr = req.params(":id");
    long idVal;
    try {
      idVal = Long.parseLong(idStr);
    } catch (NumberFormatException e) {
      throw new BadRequestException("Invalid article id");
    }
    ArticleId id = new ArticleId(idVal);
    articleRepository.deleteById(id);
    // Также удаляем комментарии к статье
    for (var c : commentRepository.findByArticleId(id)) {
      commentRepository.deleteById(c.getId());
    }
    res.status(204);
    log.debug("DELETE /articles/:id success");
    return "";
  }
}
