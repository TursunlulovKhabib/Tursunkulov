package org.homework.repository;

import org.homework.exception.NotFoundException;
import org.homework.model.Article;
import org.homework.model.ArticleId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapArticleRepository implements ArticleRepository {
  private final Map<ArticleId, Article> articles = new ConcurrentHashMap<>();
  private volatile long sequence = 0;

  @Override
  public List<Article> findAll() {
    return new ArrayList<>(articles.values());
  }

  @Override
  public Article findById(ArticleId id) {
    Article article = articles.get(id);
    if (article == null) {
      throw new NotFoundException("Article not found");
    }
    return article;
  }

  @Override
  public Article save(Article entity) {
    articles.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public void deleteById(ArticleId id) {
    if (articles.remove(id) == null) {
      throw new NotFoundException("Article not found");
    }
  }

  @Override
  public ArticleId generateId() {
    long newId = ++sequence;
    return new ArticleId(newId);
  }
}
