package homework4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class SortTest {
  @Test
  public void testSortInvalidIndex() {
    List<Integer> list = Arrays.asList(3, 2, 1);
    int numberOfSort = -1;
    List<SortStrategy> strategies = new ArrayList<>();
    strategies.add(new BubbleSort());
    strategies.add(new MergeSort());
    Sort context = new Sort(strategies);
    String result = context.sort(list, numberOfSort);
    result = context.sort(list, numberOfSort);
    System.out.println(result);
    result = context.sort(list, numberOfSort);
    assertEquals("Ошибка! Неправильный индекс сортировки.", result);
  }
}
