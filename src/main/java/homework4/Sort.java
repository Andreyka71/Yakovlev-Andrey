package homework4;

import java.util.List;

public class Sort {
  private List<SortStrategy> strategies;
  public Sort(List<SortStrategy> strategies) {
    this.strategies = strategies;
  }
  public String sort(List<Integer> list, int strategyIndex) {
    if (strategyIndex < 0 || strategyIndex >= strategies.size()) {
      return "Ошибка! Неправильный индекс сортировки.";
    }
    SortStrategy strategy = strategies.get(strategyIndex);
    try {
      List<Integer> sortedList = strategy.sort(list);
      return "Отсортированный список: " + sortedList;
      } catch (Exception e) {
        return e.getMessage();
      }
    }
}