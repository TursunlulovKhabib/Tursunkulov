package sorting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Сортирует список, использующий Collections.sort(), который использует merge sort.
 */
public class MergeSortAlgorithm implements SortAlgorithm {

  @Override
  public List<Integer> sort(List<Integer> list) throws SortException {
    if (list.size() > getMaxElements()) {
      throw new SortException("Список превышает максимально допустимые элементы для сортировки слиянием");
    }
    List<Integer> sortedList = new ArrayList<>(list);
    Collections.sort(sortedList);
    return sortedList;
  }

  @Override
  public int getMaxElements() {
    return 1000; // Произвольный предел для демонстрации
  }
}