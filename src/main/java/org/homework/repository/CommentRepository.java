package org.homework.repository;

import org.homework.model.Comment;
import org.homework.model.CommentId;
import org.homework.model.ArticleId;

import java.util.List;

public interface CommentRepository extends Repository<CommentId, Comment> {
  List<Comment> findByArticleId(ArticleId articleId);
  CommentId generateId();
}

