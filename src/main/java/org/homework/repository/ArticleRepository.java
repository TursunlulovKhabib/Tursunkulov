package org.homework.repository;

import org.homework.model.Article;
import org.homework.model.ArticleId;

import java.util.List;

public interface ArticleRepository extends Repository<ArticleId, Article> {
  List<Article> findAll();
  ArticleId generateId();
}

