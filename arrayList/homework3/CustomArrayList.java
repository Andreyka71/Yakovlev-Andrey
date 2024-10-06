package arrayList.homework3;
interface Functional<T>  {
  public void add(T element);
  public T get(int index);
  public void remove(int index);
}
public class CustomArrayList<T> implements Functional<T> { 
  private Object[] array;
  private int size;
  public CustomArrayList() {
    this.array = new Object[100];
    this.size = 0;
  }
  public void add(T element) {
    if (size == array.length) {
      extension();
    }
    array[size] = element;
    size++;
  }
  public T get(int index) {
    return (T) array[index];
  }
  public void remove(int index) {
    for (int i = index; i < size - 1; i++) {
        array[i] = array[i + 1];
    }
    size--;
    array[size] = null;
  }
  public void print() {
    for (int i = 0; i < size; i++) {
      System.out.print(array[i] + " ");
    }
  }
  private void extension() {
    Object[] newArray = new Object[array.length * 2];
    System.arraycopy(array, 0, newArray, 0, array.length);
    array = newArray;
  }
}