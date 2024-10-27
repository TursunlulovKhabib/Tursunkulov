import org.example.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTest {

  @Test
  void shouldSucceedEnrichmentInConcurrentEnvironmentSuccessfully() throws InterruptedException {
    UserRepository userRepository = new InMemoryUserRepository();
    MessageEnricher msisdnEnricher = new MsisdnEnricher(userRepository);
    Map<Message.EnrichmentType, MessageEnricher> enrichers = new HashMap<>();
    enrichers.put(Message.EnrichmentType.MSISDN, msisdnEnricher);
    EnrichmentService enrichmentService = new EnrichmentService(enrichers);
    List<Message> enrichmentResults = new CopyOnWriteArrayList<>();
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    CountDownLatch latch = new CountDownLatch(5);

    for (int i = 0; i < 5; i++) {
      executorService.submit(() -> {
        Map<String, String> inputContent = new HashMap<>();
        inputContent.put("action", "button_click");
        inputContent.put("page", "book_card");
        inputContent.put("msisdn", "88007777777");
        Message inputMessage = new Message(inputContent, Message.EnrichmentType.MSISDN);
        enrichmentResults.add(enrichmentService.enrich(inputMessage));
        latch.countDown();
      });
    }
    latch.await();
    for (Message enrichedMessage : enrichmentResults) {
      Map<String, String> expectedContent = new HashMap<>();
      expectedContent.put("action", "button_click");
      expectedContent.put("page", "book_card");
      expectedContent.put("msisdn", "88007777777");
      expectedContent.put("firstName", "Andrey");
      expectedContent.put("lastName", "Petrov");

      assertEquals(expectedContent, enrichedMessage.getContent());
    }
    executorService.shutdown();
  }
}