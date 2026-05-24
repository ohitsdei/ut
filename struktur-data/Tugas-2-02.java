import java.util.LinkedList;
import java.util.Queue;

public class Tugas_2 {
  public static void main(String[] args) {

    Queue<Integer> queue = new LinkedList<>();
    int[] data = {10, 20, 30, 40, 50, 60, 70, 80};
    for (int i = 0; i < data.length; i++) {
      queue.add(data[i]);
      System.out.println("Queue setelah add: " + queue);
    }

    int dataKeluar = queue.remove(); // .poll() juga bisa digunakan untuk menghapus data dari queue
    System.out.println("Data yang dikeluarkan: " + dataKeluar);
    System.out.println("Queue setelah remove: " + queue);

  }
}
