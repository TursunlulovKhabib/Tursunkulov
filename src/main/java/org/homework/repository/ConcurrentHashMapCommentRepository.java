package org.homework.repository;

import org.homework.exception.NotFoundException;
import org.homework.model.ArticleId;
import org.homework.model.Comment;
import org.homework.model.CommentId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ConcurrentHashMapCommentRepository implements CommentRepository {
  private final Map<CommentId, Comment> comments = new ConcurrentHashMap<>();
  private volatile long sequence = 0;

  @Override
  public Comment findById(CommentId id) {
    Comment comment = comments.get(id);
    if (comment == null) {
      throw new NotFoundException("Comment not found");
    }
    return comment;
  }

  @Override
  public Comment save(Comment entity) {
    comments.put(entity.getId(), entity);
    return entity;
  }

  @Override
  public void deleteById(CommentId id) {
    if (comments.remove(id) == null) {
      throw new NotFoundException("Comment not found");
    }
  }

  @Override
  public List<Comment> findByArticleId(ArticleId articleId) {
    List<Comment> result = new ArrayList<>();
    for (Comment c : comments.values()) {
      if (c.getArticleId().equals(articleId)) {
        result.add(c);
      }
    }
    return result;
  }

  @Override
  public CommentId generateId() {
    long newId = ++sequence;
    return new CommentId(newId);
  }
}
