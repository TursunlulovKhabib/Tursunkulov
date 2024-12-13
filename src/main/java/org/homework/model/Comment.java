package org.homework.model;

import java.util.Objects;

public final class Comment {
  private final CommentId id;
  private final ArticleId articleId;
  private final String text;

  public Comment(CommentId id, ArticleId articleId, String text) {
    this.id = id;
    this.articleId = articleId;
    this.text = text;
  }

  public CommentId getId() {
    return id;
  }

  public ArticleId getArticleId() {
    return articleId;
  }

  public String getText() {
    return text;
  }

  public Comment withText(String newText) {
    return new Comment(this.id, this.articleId, newText);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Comment)) return false;
    Comment comment = (Comment) o;
    return Objects.equals(id, comment.id) && Objects.equals(articleId, comment.articleId) && Objects.equals(text, comment.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, articleId, text);
  }
}


