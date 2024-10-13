package homework4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MergeSortTest {
    @Test
  public void testMergeSortEmptyList() throws Exception {
    MergeSort mergesort = new MergeSort();
    List<Integer> input = Arrays.asList();
    List<Integer> expected = Arrays.asList();
    List<Integer> result = mergesort.sort(input);
    assertEquals(expected, result);
  }
  @Test
  public void testMergeSort() throws Exception {
    MergeSort mergesort = new MergeSort();
    List<Integer> input = Arrays.asList(7,6,5,4,3);
    List<Integer> expected = Arrays.asList(3,4,5,6,7);
    List<Integer> result = mergesort.sort(input);
    assertEquals(expected, result);
  }
}
