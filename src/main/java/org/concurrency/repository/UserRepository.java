package org.concurrency.repository;

import org.concurrency.model.User;
import org.concurrency.exception.UserNotFoundException;

/**
 * Интерфейс для доступа к информации о пользователях.
 * <p>
 * Предоставляет методы для поиска, обновления и удаления пользователей по MSISDN.
 * </p>
 */
public interface UserRepository {

  /**
   * Находит пользователя по MSISDN.
   *
   * @param msisdn MSISDN пользователя.
   * @return {@link User} если пользователь найден, иначе {@code null}.
   */
  User findByMsisdn(String msisdn);

  /**
   * Обновляет данные пользователя по MSISDN. Если пользователь с указанным MSISDN не существует,
   * он будет добавлен.
   *
   * @param msisdn MSISDN пользователя.
   * @param user    Новый объект {@link User}.
   */
  void updateUserByMsisdn(String msisdn, User user);

  /**
   * Удаляет пользователя по MSISDN.
   *
   * @param msisdn MSISDN пользователя.
   * @throws UserNotFoundException если пользователь с указанным MSISDN не найден.
   */
  void deleteUserByMsisdn(String msisdn) throws UserNotFoundException;
}
