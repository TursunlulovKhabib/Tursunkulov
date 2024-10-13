package org.example;

import java.util.List;

public interface SortAlgorithm {
  List<Integer> sort(List<Integer> list) throws SortException;

  int getMaxElements();
}