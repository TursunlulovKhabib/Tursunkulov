package org.homework.repository;

public interface Repository<ID, E> {
  E findById(ID id);
  E save(E entity);
  void deleteById(ID id);
}
