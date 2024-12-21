package org.concurrency;

import org.concurrency.exception.EnrichmentException;
import org.concurrency.repository.InMemoryUserRepository;
import org.concurrency.repository.UserRepository;
import org.concurrency.service.EnrichmentService;
import org.concurrency.service.MessageEnricher;
import org.concurrency.service.MsisdnEnricher;
import org.concurrency.model.Message;

import java.util.HashMap;
import java.util.Map;

public class Main {

  private static UserRepository userRepository;
  private static EnrichmentService enrichmentService;

  public static void main(String[] args) throws EnrichmentException {
    initializeServices();

    // Создаем тестовое входное сообщение
    Map<String, String> inputContent = new HashMap<>();
    inputContent.put("action", "button_click");
    inputContent.put("page", "book_card");
    inputContent.put("msisdn", "88007777777");

    Message inputMessage = new Message(inputContent, Message.EnrichmentType.MSISDN);

    // Печатаем исходное сообщение
    System.out.println("Исходное сообщение:");
    printMessage(inputMessage);

      // Обогащаем сообщение
      Message enrichedMessage = enrichmentService.enrich(inputMessage);

      // Печатаем обогащенное сообщение
      System.out.println("Обогащенное сообщение:");
      printMessage(enrichedMessage);
  }

  private static void initializeServices() {
    userRepository = new InMemoryUserRepository();
    MessageEnricher msisdnEnricher = new MsisdnEnricher(userRepository);
    Map<Message.EnrichmentType, MessageEnricher> enrichers = new HashMap<>();
    enrichers.put(Message.EnrichmentType.MSISDN, msisdnEnricher);
    enrichmentService = new EnrichmentService(enrichers);
  }

  /**
   * Утилитный метод для печати сообщения.
   *
   * @param message сообщение для печати.
   */
  private static void printMessage(Message message) {
    System.out.println("{");
    message.getContent().forEach((key, value) ->
            System.out.println("  \"" + key + "\": \"" + value + "\"")
    );
    System.out.println("}");
  }
}

