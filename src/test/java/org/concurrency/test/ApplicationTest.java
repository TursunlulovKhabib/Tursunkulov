package org.concurrency.test;

import org.concurrency.exception.EnrichmentException;
import org.concurrency.model.Message;
import org.concurrency.repository.InMemoryUserRepository;
import org.concurrency.repository.UserRepository;
import org.concurrency.service.EnrichmentService;
import org.concurrency.service.MessageEnricher;
import org.concurrency.service.MsisdnEnricher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

  private UserRepository userRepository;
  private MessageEnricher msisdnEnricher;
  private EnrichmentService enrichmentService;
  private ExecutorService executorService;

  @BeforeEach
  void setUp() {
    userRepository = new InMemoryUserRepository();
    msisdnEnricher = new MsisdnEnricher(userRepository);
    Map<Message.EnrichmentType, MessageEnricher> enrichers = new HashMap<>();
    enrichers.put(Message.EnrichmentType.MSISDN, msisdnEnricher);
    enrichmentService = new EnrichmentService(enrichers);
    executorService = Executors.newFixedThreadPool(5);
  }

  @Test
  @DisplayName("Успешное обогащение сообщений в многопоточной среде")
  void shouldSucceedEnrichmentInConcurrentEnvironmentSuccessfully() throws InterruptedException {
    // Используем потокобезопасную коллекцию для результатов
    List<Message> enrichmentResults = Collections.synchronizedList(new ArrayList<>());
    CountDownLatch latch = new CountDownLatch(5);

    // Запускаем 5 параллельных задач для обогащения сообщений
    for (int i = 0; i < 5; i++) {
      executorService.submit(() -> {
        try {
          Map<String, String> inputContent = new HashMap<>();
          inputContent.put("action", "button_click");
          inputContent.put("page", "book_card");
          inputContent.put("msisdn", "88007777777");
          Message inputMessage = new Message(inputContent, Message.EnrichmentType.MSISDN);
            Message enrichedMessage = null;
            try {
                enrichedMessage = enrichmentService.enrich(inputMessage);
            } catch (EnrichmentException e) {
                throw new RuntimeException(e);
            }
            enrichmentResults.add(enrichedMessage);
        } finally {
          latch.countDown();
        }
      });
    }

    // Ожидаем завершения всех задач
    latch.await();

    // Проверяем результаты обогащения
    Map<String, String> expectedContent = new HashMap<>();
    expectedContent.put("action", "button_click");
    expectedContent.put("page", "book_card");
    expectedContent.put("msisdn", "88007777777");
    expectedContent.put("firstName", "Andrey");
    expectedContent.put("lastName", "Petrov");

    for (Message enrichedMessage : enrichmentResults) {
      assertEquals(expectedContent, enrichedMessage.getContent(),
              "Содержимое обогащённого сообщения не соответствует ожидаемому");
    }

    executorService.shutdown();
  }
}