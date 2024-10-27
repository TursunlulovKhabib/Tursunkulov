package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Реализация UserRepository, хранящая данные в памяти.
 */
public class InMemoryUserRepository implements UserRepository {

  private final Map<String, User> userStore = new ConcurrentHashMap<>();

  public InMemoryUserRepository() {
    // Добавьте пример пользователей
    userStore.put("88007777777", new User("Andrey", "Petrov"));
  }

  @Override
  public User findByMsisdn(String msisdn) {
    return userStore.get(msisdn);
  }

  @Override
  public void updateUserByMsisdn(String msisdn, User user) {
    userStore.put(msisdn, user);
  }
}