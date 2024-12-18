package org.concurrency.repository;

import org.concurrency.model.User;
import org.concurrency.exception.UserNotFoundException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Реализация {@link UserRepository}, хранящая данные в памяти.
 * <p>
 * Этот класс предоставляет методы для поиска, обновления и удаления пользователей по MSISDN.
 * </p>
 */
public final class InMemoryUserRepository implements UserRepository {

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

  @Override
  public void deleteUserByMsisdn(String msisdn) throws UserNotFoundException {
    User removedUser = userStore.remove(msisdn);
    if (removedUser == null) {
      throw new UserNotFoundException("Пользователь с MSISDN " + msisdn + " не найден.");
    }
  }
}

