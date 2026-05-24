import java.util.Arrays;

public class Tugas_2 {
  public static void main(String[] args) {

    // method quick sort

    int[] data = {27, 25, 20, 15, 10, 5, 1, 88};
    System.out.println("Data sebelum diurutkan: " + Arrays.toString(data));

    quickSort(data, 0, data.length - 1);
    System.out.println("Data setelah diurutkan: " + Arrays.toString(data)); 
  }

  public static void quickSort(int[] data, int left, int right) {

    if (left < right) {
      int pivot = partition(data, left, right);
      quickSort(data, left, pivot - 1);
      quickSort(data, pivot + 1, right);
    }
  }

  public static int partition(int[] data, int left, int right) {
    int pivot = data[right];
    int i = left - 1;
    for (int j = left; j < right; j++) {
      if (data[j] < pivot) {
        i++;
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
      }
    }
    int temp = data[i + 1];
    data[i + 1] = data[right];
    data[right] = temp;
    return i + 1;
  }
}
