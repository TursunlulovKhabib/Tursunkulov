package org.concurrency.service;

import org.concurrency.exception.EnrichmentException;
import org.concurrency.model.Message;
import org.concurrency.model.EnrichmentType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Сервис для обогащения сообщений.
 * <p>
 * Использует карту {@link EnrichmentType} к соответствующим {@link MessageEnricher} для обогащения содержимого сообщения.
 * </p>
 */
public final class EnrichmentService {

  private static final Logger LOGGER = Logger.getLogger(EnrichmentService.class.getName());

  private final Map<Message.EnrichmentType, MessageEnricher> enrichers;

  /**
   * Конструктор для создания сервиса обогащения сообщений.
   *
   * @param enrichers Карта типов обогащений к соответствующим обогатителям.
   * @throws IllegalArgumentException если карта {@code enrichers} равна {@code null}.
   */
  public EnrichmentService(Map<Message.EnrichmentType, MessageEnricher> enrichers) {
    this.enrichers = Collections.unmodifiableMap(new EnumMap<>(Objects.requireNonNull(enrichers, "Карта enrichers не может быть null")));
  }


  /**
   * Обогащает данное сообщение с использованием соответствующего {@link MessageEnricher}.
   *
   * @param message Сообщение для обогащения.
   * @return Обогащённое сообщение, или исходное сообщение, если соответствующий обогатитель не найден.
   * @throws EnrichmentException если произошла ошибка при обогащении сообщения.
   */
  public Message enrich(Message message) throws EnrichmentException {
    if (message == null) {
      LOGGER.warning("Входное сообщение равно null. Возвращаем null без обогащения.");
      return null;
    }

    Message.EnrichmentType enrichmentType = message.getEnrichmentType();
    if (enrichmentType == null) {
      LOGGER.warning("Тип обогащения сообщения равен null. Возвращаем исходное сообщение.");
      return message;
    }

    MessageEnricher enricher = enrichers.get(enrichmentType);
    if (enricher == null) {
      LOGGER.info("Обогатитель для типа " + enrichmentType + " не найден. Возвращаем исходное сообщение.");
      return message;
    }

    try {
      Map<String, String> enrichedContent = enricher.enrich(message.getContent());
      return new Message(enrichedContent, enrichmentType);
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Ошибка при обогащении сообщения: " + e.getMessage(), e);
      throw new EnrichmentException("Не удалось обогатить сообщение.", e);
    }
  }
}

