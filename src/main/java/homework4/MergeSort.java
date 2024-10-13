package homework4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MergeSort implements SortStrategy {
  static final int MAX_SIZE = 10000;
  public List<Integer> sort(List<Integer> list) throws Exception {
    if (list.size() > MAX_SIZE) {
      throw new Exception("Ошибка! Превышено количество элементов для этой сортировки!");
    }
    List<Integer> sortedList = new ArrayList<>(list);
    Collections.sort(sortedList);
    return sortedList;
  }
}