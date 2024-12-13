package org.homework.model;

import java.util.Objects;

public final class CommentId {
  private final long value;

  public CommentId(long value) {
    this.value = value;
  }

  public long value() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CommentId)) return false;
    CommentId commentId = (CommentId) o;
    return value == commentId.value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}


