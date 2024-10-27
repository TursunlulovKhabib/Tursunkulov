package org.example;

import java.util.Map;

/**
 * Класс, представляющий пользовательское сообщение.
 */
public class Message {

  private Map<String, String> content;
  private EnrichmentType enrichmentType;

  public enum EnrichmentType {
    MSISDN
  }

  public Message(Map<String, String> content, EnrichmentType enrichmentType) {
    this.content = content;
    this.enrichmentType = enrichmentType;
  }

  public Map<String, String> getContent() {
    return content;
  }

  public EnrichmentType getEnrichmentType() {
    return enrichmentType;
  }
}