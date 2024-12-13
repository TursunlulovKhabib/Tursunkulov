package org.homework;

import org.homework.controller.ArticleController;
import org.homework.controller.CommentController;
import org.homework.exception.BadRequestException;
import org.homework.exception.NotFoundException;
import org.homework.repository.ArticleRepository;
import org.homework.repository.CommentRepository;
import org.homework.repository.ConcurrentHashMapArticleRepository;
import org.homework.repository.ConcurrentHashMapCommentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application {
  private static final Logger log = LoggerFactory.getLogger(Application.class);

  private final ArticleRepository articleRepository;
  private final CommentRepository commentRepository;
  private final ArticleController articleController;
  private final CommentController commentController;
  private final ObjectMapper objectMapper = new ObjectMapper();

  public Application() {
    this.articleRepository = new ConcurrentHashMapArticleRepository();
    this.commentRepository = new ConcurrentHashMapCommentRepository();
    this.articleController = new ArticleController(articleRepository, commentRepository);
    this.commentController = new CommentController(articleRepository, commentRepository);
  }

  public void start() {
    Spark.port(4567);

    // Роуты статей
    Spark.get("/articles", (req, res) -> articleController.getAllArticles(req, res));
    Spark.get("/articles/:id", (req, res) -> articleController.getArticle(req, res));
    Spark.post("/articles", (req, res) -> articleController.createArticle(req, res));
    Spark.put("/articles/:id", (req, res) -> articleController.updateArticle(req, res));
    Spark.delete("/articles/:id", (req, res) -> articleController.deleteArticle(req, res));

    // Роуты комментариев
    Spark.post("/articles/:articleId/comments", (req, res) -> commentController.createComment(req, res));
    Spark.delete("/comments/:commentId", (req, res) -> commentController.deleteComment(req, res));

    // Шаблонный эндпоинт
    Spark.get("/articles_view", (req, res) -> {
      List<Map<String, ? extends Serializable>> articles = articleRepository.findAll().stream().map(a -> {
        var comments = commentRepository.findByArticleId(a.getId());
        return Map.of(
            "title", a.getTitle(),
            "commentsCount", comments.size()
        );
      }).collect(Collectors.toList());

      var html = TemplateFactory.render("articles.peb", Map.of("articles", articles));
      res.type("text/html");
      return html;
    });

    Spark.exception(NotFoundException.class, (ex, req, res) -> {
      log.warn("4xx Not Found error: {}", ex.getMessage());
      res.status(404);
      res.type("application/json");
      res.body("{\"message\":\"" + ex.getMessage() + "\"}");
    });

    Spark.exception(BadRequestException.class, (ex, req, res) -> {
      log.warn("4xx Bad Request error: {}", ex.getMessage());
      res.status(400);
      res.type("application/json");
      res.body("{\"message\":\"" + ex.getMessage() + "\"}");
    });

    Spark.exception(Exception.class, (ex, req, res) -> {
      log.error("5xx Server error: {}", ex.getMessage(), ex);
      res.status(500);
      res.type("application/json");
      res.body("{\"message\":\"Internal server error\"}");
    });

    Spark.after((req, res) -> {
      if (res.status() < 400) {
        log.debug("Request {} {} completed successfully with status {}", req.requestMethod(), req.pathInfo(), res.status());
      }
    });
  }
}

