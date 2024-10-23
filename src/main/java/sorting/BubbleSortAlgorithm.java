package sorting;

import java.util.ArrayList;
import java.util.List;

/**
 * Сортирует список с помощью алгоритма пузырьковой сортировки.
 */
public class BubbleSortAlgorithm implements SortAlgorithm {

  @Override
  public List<Integer> sort(List<Integer> list) throws SortException {
    if (list.size() > getMaxElements()) {
      throw new SortException("Список превышает максимально допустимые элементы для пузырьковой сортировки");
    }
    List<Integer> sortedList = new ArrayList<>(list);
    bubbleSort(sortedList);
    return sortedList;
  }

  @Override
  public int getMaxElements() {
    return 500; // Произвольный предел для демонстрации
  }

  // Немного похоже на приложение 1 из файла practise.md
  private void bubbleSort(List<Integer> list) {
    int n = list.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (list.get(j) > list.get(j + 1)) {
          int temp = list.get(j);
          list.set(j, list.get(j + 1));
          list.set(j + 1, temp);
        }
      }
    }
  }
}