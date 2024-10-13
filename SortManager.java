package org.example;

import java.util.ArrayList;
import java.util.List;

public class SortManager {
  private final List<SortAlgorithm> algorithms = new ArrayList<>();

  public void addAlgorithm(SortAlgorithm algorithm) {
    algorithms.add(algorithm);
  }

  public List<Integer> sort(List<Integer> list, Class<? extends SortAlgorithm> algorithmType) throws SortException {
    for (SortAlgorithm algorithm : algorithms) {
      if (algorithm.getClass().equals(algorithmType)) {
        try {
          return algorithm.sort(list);
        } catch (SortException e) {
          // Переходит к следующему алгоритму
        }
      }
    }
    throw new SortException("Не найден подходящий алгоритм сортировки");
  }
}
