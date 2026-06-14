/*

2.Buatlah program pencarian menggunakan algoritma Breadth-First Search (BFS) dalam bahasa Java. (35 Poin)

- Gunakan graf dengan 10 node yang mewakili  a0, a1, a2, a3, a4, a5, a6, a7 , a8, a9 (tentukan sendiri nilai hubungan antar node).
- Lakukan pencarian angka n pada graf tersebut dengan BFS.
- Jelaskan tahap demi tahap proses pencarian dan gambarkan dengan rinci prosesnya.
*/

/* Stuktur graf

        a0
       /  \
     a1    a2
    /  \   /  \
  a3   a4 a5   a6
   |    \  /    |
  a7    a8     a9
   \____________/

*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Tugas_3_2 {

    static Map<String, List<String>> graph = new HashMap<>();

    public static void main(String[] args) {
        graph.put("a0", Arrays.asList("a1", "a2"));
        graph.put("a1", Arrays.asList("a0", "a3", "a4"));
        graph.put("a2", Arrays.asList("a0", "a5", "a6"));
        graph.put("a3", Arrays.asList("a1", "a7"));
        graph.put("a4", Arrays.asList("a1", "a8"));
        graph.put("a5", Arrays.asList("a2", "a8"));
        graph.put("a6", Arrays.asList("a2", "a9"));
        graph.put("a7", Arrays.asList("a3", "a9"));
        graph.put("a8", Arrays.asList("a4", "a5"));
        graph.put("a9", Arrays.asList("a6", "a7"));

        String start = "a0";
        String target = "a9";
        bfs(start, target);
    }

    static void bfs(String start, String target) {
        Set<String> visited = new LinkedHashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parent = new HashMap<>();

        System.out.println("Mulai pencarian dari node " + start + " mencari node " + target);
        
        queue.add(start);
        visited.add(start);
        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();
            System.out.println("Mengunjungi node: " + current);
            
            if (current.equals(target)) {
                found = true;
                break;
            }
            for (String neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                }
            }
        }
        System.out.println(found ? "Node " + target + " DITEMUKAN" : "Node " + target + " TIDAK DITEMUKAN");
        if (found) {
            List<String> path = new ArrayList<>();
            String node = target;
            while (node != null) {
                path.add(0, node);
                node = parent.get(node);
            }
            System.out.println("Jalur pencarian: " + path);
        }
    }
}
