package homework4;

import java.util.ArrayList;
import java.util.List;

public class BubbleSort implements SortStrategy {
  private static final int MAX_SIZE = 100;
  public List<Integer> sort(List<Integer> list) throws Exception {
    if (list.size() > MAX_SIZE) {
      throw new Exception("Ошибка! Превышено количество элементов для этой сортировки!");
    }
    List<Integer> sortedList = new ArrayList<>(list);
    int n = sortedList.size();
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (sortedList.get(j) > sortedList.get(j + 1)) {
          int temp = sortedList.get(j);
          sortedList.set(j, sortedList.get(j + 1));
          sortedList.set(j + 1, temp);
        }
      }
    }
    return sortedList;
  }
}