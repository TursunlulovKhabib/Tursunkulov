package org.homework;

import org.homework.Application;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2ETest {

  private static Application app;

  @BeforeClass
  public static void setup() throws InterruptedException {
    app = new Application();
    app.start();
    Thread.sleep(1000); // подождем пока Spark поднимется
  }

  @AfterClass
  public static void tearDown() {
    // Отключить Spark
    spark.Spark.stop();
  }

  @Test
  public void testFullScenario() {
    // 1. Создаем статью
    HttpResponse<Map> createResponse = Unirest.post("http://localhost:4567/articles")
        .header("Content-Type", "application/json")
        .body("{\"title\":\"My Article\", \"tags\": [\"news\", \"tech\"]}")
        .asObject(Map.class);
    assertEquals(201, createResponse.getStatus());
    Long articleId = ((Number)createResponse.getBody().get("id.value")).longValue();

    // 2. Добавляем комментарий
    HttpResponse<Map> commentResponse = Unirest.post("http://localhost:4567/articles/" + articleId + "/comments")
        .header("Content-Type","application/json")
        .body("{\"text\":\"My Comment\"}")
        .asObject(Map.class);
    assertEquals(201, commentResponse.getStatus());
    Long commentId = ((Number)commentResponse.getBody().get("id.value")).longValue();

    // 3. Редактируем статью
    HttpResponse<Map> updateResponse = Unirest.put("http://localhost:4567/articles/" + articleId)
        .header("Content-Type","application/json")
        .body("{\"title\":\"My Updated Article\"}")
        .asObject(Map.class);
    assertEquals(200, updateResponse.getStatus());

    // 4. Удаляем комментарий
    HttpResponse<String> deleteCommentResponse = Unirest.delete("http://localhost:4567/comments/" + commentId).asString();
    assertEquals(204, deleteCommentResponse.getStatus());

    // 5. Запрашиваем статью по ID и проверяем
    HttpResponse<Map> getArticleResponse = Unirest.get("http://localhost:4567/articles/" + articleId).asObject(Map.class);
    assertEquals(200, getArticleResponse.getStatus());
    assertEquals("My Updated Article", getArticleResponse.getBody().get("title"));
    // Проверяем, что нет комментариев
    assertTrue(((java.util.List) getArticleResponse.getBody().get("comments")).isEmpty());
  }
}
