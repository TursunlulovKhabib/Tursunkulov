package sorting;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    java.util.List<Integer> numbers = Arrays.asList(5, 3, 8, 4, 2);

    SortManager sortManager = new SortManager();
    sortManager.addAlgorithm(new MergeSortAlgorithm());
    sortManager.addAlgorithm(new BubbleSortAlgorithm());

    try {
      java.util.List<Integer> sortedList = sortManager.sort(numbers, MergeSortAlgorithm.class);
      System.out.println("Sorted using MergeSort: " + sortedList);
    } catch (SortException e) {
      e.printStackTrace();
    }

    try {
      List<Integer> sortedList = sortManager.sort(numbers, BubbleSortAlgorithm.class);
      System.out.println("Sorted using BubbleSort: " + sortedList);
    } catch (SortException e) {
      e.printStackTrace();
    }
  }
}
