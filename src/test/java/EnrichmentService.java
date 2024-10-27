import org.example.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class EnrichmentServiceTest {

  private EnrichmentService enrichmentService;

  @BeforeEach
  void setUp() {
    UserRepository userRepository = new InMemoryUserRepository();
    MessageEnricher msisdnEnricher = new MsisdnEnricher(userRepository);
    Map<Message.EnrichmentType, MessageEnricher> enrichers = new HashMap<>();
    enrichers.put(Message.EnrichmentType.MSISDN, msisdnEnricher);
    enrichmentService = new EnrichmentService(enrichers);
  }

  @Test
  void testEnrich() {
    Map<String, String> inputContent = new HashMap<>();
    inputContent.put("action", "button_click");
    inputContent.put("page", "book_card");
    inputContent.put("msisdn", "88007777777");
    Message inputMessage = new Message(inputContent, Message.EnrichmentType.MSISDN);
    Message enrichedMessage = enrichmentService.enrich(inputMessage);
    Map<String, String> expectedContent = new HashMap<>(inputContent);
    expectedContent.put("firstName", "Andrey");
    expectedContent.put("lastName", "Petrov");
    assertEquals(expectedContent, enrichedMessage.getContent());
  }

  @Test
  void testEnrich_whenMsisdnIsAbsent() {
    Map<String, String> inputContent = new HashMap<>();
    inputContent.put("action", "button_click");
    Message inputMessage = new Message(inputContent, Message.EnrichmentType.MSISDN);
    Message enrichedMessage = enrichmentService.enrich(inputMessage);
    assertEquals(inputContent, enrichedMessage.getContent());
  }
}