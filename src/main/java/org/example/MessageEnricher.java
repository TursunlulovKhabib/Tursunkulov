package org.example;

import java.util.Map;

/**
 * Интерфейс для обогащения сообщения.
 */
public interface MessageEnricher {
  Map<String, String> enrich(Map<String, String> content);
}