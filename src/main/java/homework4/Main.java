package homework4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    System.out.println("Введите количество элементов:");
    Scanner input = new Scanner(System.in);
    int numberOfElements = input.nextInt();
    List<Integer> list = new ArrayList<>();
    System.out.println("Введите элементы через пробел:");
    for (int i = 0; i < numberOfElements; i++) {
      list.add(input.nextInt());
    }
    System.out.println("Введите номер сортировки.Пузырёк - 0, Слиянием - 1.Ваш выбор:");
    int numberOfSort = input.nextInt();
    List<SortStrategy> strategies = new ArrayList<>();
    strategies.add(new BubbleSort());
    strategies.add(new MergeSort());
    Sort context = new Sort(strategies);
    String result = context.sort(list, numberOfSort);
    result = context.sort(list, numberOfSort);
    System.out.println(result);
    result = context.sort(list, numberOfSort);
  }
}