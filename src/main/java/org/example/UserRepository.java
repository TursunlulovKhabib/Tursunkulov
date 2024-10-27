package org.example;

/**
 * Интерфейс для доступа к информации о пользователях.
 */
public interface UserRepository {
  User findByMsisdn(String msisdn);
  void updateUserByMsisdn(String msisdn, User user);
}