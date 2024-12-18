package org.concurrency.model;

/**
 * Класс, представляющий информацию о пользователе.
 * <p>
 * Этот класс является неизменяемым и безопасным для использования в многопоточной среде.
 * </p>
 */
public final class User {
  private final String firstName;
  private final String lastName;

  public User(String firstName, String lastName) {
    if (firstName == null || lastName == null) {
      throw new IllegalArgumentException("Имя и фамилия не могут быть null");
    }
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (!firstName.equals(user.firstName)) return false;
    return lastName.equals(user.lastName);
  }

  @Override
  public int hashCode() {
    int result = firstName.hashCode();
    result = 31 * result + lastName.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "User{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
  }
}

