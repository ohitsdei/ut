/*
1. Buatlah program pencarian menggunakan algoritma Depth-First Search (DFS) dalam bahasa Java. (35 Poin)

- Gunakan graf dengan 10 node yang mewakili a0, a1, a2, a3, a4, a5, a6, a7, a8, a9 (tentukan sendiri nilai hubungan antar node). 
- Lakukan pencarian angka n pada graf tersebut dengan DFS. 
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

*/

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tugas_3_1 {

    static Map<String, List<String>> graph = new HashMap<>();
    public static void main(String[] args) {
        graph.put("a0", Arrays.asList("a1", "a2"));
        graph.put("a1", Arrays.asList("a0", "a3", "a4"));
        graph.put("a2", Arrays.asList("a0", "a5", "a6"));
        graph.put("a3", Arrays.asList("a1", "a7"));
        graph.put("a4", Arrays.asList("a1", "a8"));
        graph.put("a5", Arrays.asList("a2", "a9"));
        graph.put("a6", Arrays.asList("a2", "a9"));
        graph.put("a7", Arrays.asList("a3"));
        graph.put("a8", Arrays.asList("a4", "a5"));
        graph.put("a9", Arrays.asList("a6"));

        String start = "a0";
        String target = "a9";
        Set<String> visited = new LinkedHashSet<>();

        System.out.println("Mulai pencarian dari node " + start + " mencari node " + target);
        boolean found = dfs(start, target, visited);

        System.out.println("\nUrutan kunjungan node: " + visited);
        System.out.println(found ? "Node " + target + " DITEMUKAN" : "Node " + target + " TIDAK DITEMUKAN");
    }

    static boolean dfs(String current, String target, Set<String> visited) {
        if (visited.contains(current)) {
            return false;
        }
        visited.add(current);
        System.out.println("Mengunjungi node: " + current);
        if (current.equals(target)) {
            return true;
        }
        for (String neighbor : graph.get(current)) {
            if (dfs(neighbor, target, visited)) {
                return true;
            }
        }
        return false;
    }
}
