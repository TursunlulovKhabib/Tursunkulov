package org.example;

import java.util.Map;

/**
 * Класс для обогащения сообщения по типу MSISDN.
 */
public class MsisdnEnricher implements MessageEnricher {

  private final UserRepository userRepository;

  public MsisdnEnricher(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Map<String, String> enrich(Map<String, String> content) {
    String msisdn = content.get("msisdn");
    if (msisdn == null) {
      return content;
    }
    User user = userRepository.findByMsisdn(msisdn);
    if (user == null) {
      return content;
    }
    content.put("firstName", user.getFirstName());
    content.put("lastName", user.getLastName());
    return content;
  }
}