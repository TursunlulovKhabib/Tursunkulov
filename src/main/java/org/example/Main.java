package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {

  public static void main(String[] args) {
    UserRepository userRepository = new InMemoryUserRepository();

    MessageEnricher msisdnEnricher = new MsisdnEnricher(userRepository);

    Map<Message.EnrichmentType, MessageEnricher> enrichers = new HashMap<>();
    enrichers.put(Message.EnrichmentType.MSISDN, msisdnEnricher);

    EnrichmentService enrichmentService = new EnrichmentService(enrichers);

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
