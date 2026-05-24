import java.util.Stack;

public class Tugas_2 {
  public static void main(String[] args) {

    Stack<Integer> stack = new Stack<>();
    int[] data = {10, 20, 30, 40, 50, 60, 70, 80};

    for (int i = 0; i < data.length; i++) {
      stack.push(data[i]);
      System.out.println("Stack setelah push: " + stack);
    }
    System.out.println("Stack sebelum pop: " + stack);

    stack.pop();
    System.out.println("Stack setelah pop: " + stack);
  }
}
