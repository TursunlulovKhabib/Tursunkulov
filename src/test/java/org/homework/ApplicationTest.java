package org.homework;

import org.homework.model.Article;
import org.homework.model.ArticleId;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class ApplicationTest {

  @Test
  public void testArticleImmutable() {
    Article a = new Article(new ArticleId(1L), "Title", Set.of("tag1"), List.of());
    Article a2 = a.withTitle("New Title");
    Assert.assertNotEquals(a.getTitle(), a2.getTitle());
  }
}
