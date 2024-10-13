package org.example;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BubbleSortAlgorithmTest {

  @Test
  void testSort() throws SortException {
    BubbleSortAlgorithm algorithm = new BubbleSortAlgorithm();
    List<Integer> list = Arrays.asList(5, 3, 8, 4, 2);
    List<Integer> sortedList = algorithm.sort(list);
    assertEquals(Arrays.asList(2, 3, 4, 5, 8), sortedList);
  }

  @Test
  void testSortExceedsMaxElements() {
    BubbleSortAlgorithm algorithm = new BubbleSortAlgorithm();
    List<Integer> list = new ArrayList<>(Collections.nCopies(501, 1));
    assertThrows(SortException.class, () -> algorithm.sort(list));
  }
}