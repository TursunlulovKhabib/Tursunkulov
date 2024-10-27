package org.example;

import java.util.Map;

/**
 * Класс для обогащения сообщений.
 */
public class EnrichmentService {

  private final Map<Message.EnrichmentType, MessageEnricher> enrichers;

  public EnrichmentService(Map<Message.EnrichmentType, MessageEnricher> enrichers) {
    this.enrichers = enrichers;
  }

  public Message enrich(Message message) {
    MessageEnricher enricher = enrichers.get(message.getEnrichmentType());
    if (enricher == null) {
      return message;
    }
    Map<String, String> enrichedContent = enricher.enrich(message.getContent());
    return new Message(enrichedContent, message.getEnrichmentType());
  }
}
