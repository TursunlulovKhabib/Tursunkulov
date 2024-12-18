package org.concurrency.service;

import org.concurrency.model.User;
import org.concurrency.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Обогатитель сообщений по типу MSISDN.
 * <p>
 * Добавляет информацию о пользователе (имя и фамилию) в содержимое сообщения на основе MSISDN.
 * </p>
 */
public final class MsisdnEnricher implements MessageEnricher {

  private static final Logger LOGGER = Logger.getLogger(MsisdnEnricher.class.getName());

  private final UserRepository userRepository;

  public MsisdnEnricher(UserRepository userRepository) {
    this.userRepository = Objects.requireNonNull(userRepository, "UserRepository не может быть null");
  }

  /**
   * Обогащает содержимое сообщения, добавляя имя и фамилию пользователя на основе MSISDN.
   *
   * @param content Содержимое сообщения для обогащения.
   * @return Обогащённое содержимое сообщения.
   */

  @Override
  public Map<String, String> enrich(Map<String, String> content) {
    if (content == null) {
      LOGGER.warning("Входное содержимое равно null. Возвращаем null без обогащения.");
      return null;
    }

    String msisdn = content.get("msisdn");
    if (msisdn == null) {
      LOGGER.info("MSISDN отсутствует в содержимом сообщения. Возвращаем исходное содержимое.");
      return content;
    }

    User user = userRepository.findByMsisdn(msisdn);
    if (user == null) {
      LOGGER.info("Пользователь с MSISDN " + msisdn + " не найден. Возвращаем исходное содержимое.");
      return content;
    }

    Map<String, String> enrichedContent = new HashMap<>(content);
    enrichedContent.put("firstName", user.getFirstName());
    enrichedContent.put("lastName", user.getLastName());

    LOGGER.info("Сообщение успешно обогатилось данными пользователя: " + user);
    return enrichedContent;
  }
}
