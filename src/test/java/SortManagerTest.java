import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import sorting.*;

class SortManagerTest {

  @Test
  void testSortWithMergeSort() throws SortException {
    SortManager manager = new SortManager();
    manager.addAlgorithm(new MergeSortAlgorithm());

    List<Integer> list = Arrays.asList(5, 3, 8, 4, 2);
    List<Integer> sortedList = manager.sort(list, MergeSortAlgorithm.class);
    assertEquals(Arrays.asList(2, 3, 4, 5, 8), sortedList);
  }

  @Test
  void testSortWithBubbleSort() throws SortException {
    SortManager manager = new SortManager();
    manager.addAlgorithm(new BubbleSortAlgorithm());

    List<Integer> list = Arrays.asList(5, 3, 8, 4, 2);
    List<Integer> sortedList = manager.sort(list, BubbleSortAlgorithm.class);
    assertEquals(Arrays.asList(2, 3, 4, 5, 8), sortedList);
  }

  @Test
  void testNoAlgorithmThrowsException() {
    SortManager manager = new SortManager();

    List<Integer> list = Arrays.asList(5, 3, 8, 4, 2);
    assertThrows(SortException.class, () -> manager.sort(list, MergeSortAlgorithm.class));
  }

  @Test
  void testSortWithUnknownAlgorithm() {
    SortManager manager = new SortManager();
    manager.addAlgorithm(new MergeSortAlgorithm());

    List<Integer> list = Arrays.asList(5, 3, 8, 4, 2);
    assertThrows(SortException.class, () -> manager.sort(list, BubbleSortAlgorithm.class));
  }
}