package arrayList.homework3;

public class Main {
  public static void main(String[] args) {
    CustomArrayList<Integer> array = new CustomArrayList();
    array.add(4);
    array.add(6);
    array.add(7);
    int a = array.get(1);
    System.out.println(a);
    array.remove(1);
    a = array.get(1);
    System.out.println(a);
  }
}