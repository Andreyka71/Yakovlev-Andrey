package homework4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

public class BubbleSortTest {
  @Test
  public void testBubbleSortEmptyList() throws Exception {
    BubbleSort bubbleSort = new BubbleSort();
    List<Integer> input = Arrays.asList();
    List<Integer> expected = Arrays.asList();
    List<Integer> result = bubbleSort.sort(input);
    assertEquals(expected, result);
  }
  @Test
  public void testBubbleSort() throws Exception {
    BubbleSort bubbleSort = new BubbleSort();
    List<Integer> input = Arrays.asList(7,6,5,4,3);
    List<Integer> expected = Arrays.asList(3,4,5,6,7);
    List<Integer> result = bubbleSort.sort(input);
    assertEquals(expected, result);
  }
}
