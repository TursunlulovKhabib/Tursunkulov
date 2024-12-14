package org.homework.repository;

import org.homework.ConcurrentHashMapCommentRepository;
import org.homework.model.ArticleId;
import org.homework.model.Comment;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ConcurrentHashMapCommentRepositoryTest {
    @Test
    public void testCreateAndFind() {
      var repo = new ConcurrentHashMapCommentRepository();
      var aId = new ArticleId(1L);
      var cId = repo.generateId();
      var comment = new Comment(cId, aId, "Hello");
      repo.save(comment);

      var found = repo.findById(cId);
      Assert.assertEquals("Hello", found.getText());
      List<Comment> byArticle = repo.findByArticleId(aId);
      Assert.assertEquals(1, byArticle.size());
      Assert.assertEquals(cId, byArticle.get(0).getId());
    }
  }