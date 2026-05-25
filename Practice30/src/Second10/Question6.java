package Second10;

import java.util.*;

public class Question6 {
    public static int maxCities(int[] a, int[] b) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Set<String> set = new HashSet<>();

        for (int i = 0; i < a.length; i++) {
            String curr = Math.min(a[i], b[i]) + "#" + Math.max(a[i], b[i]);
            if (!set.contains(curr)) {
                set.add(curr);
                graph.computeIfAbsent(a[i], k -> new ArrayList<>()).add(b[i]);
                graph.computeIfAbsent(b[i], k -> new ArrayList<>()).add(a[i]);
            }
        }

        int maxCities = 0;
        Set<Integer> visited = new HashSet<>();
        for (int node : graph.keySet()) {

            if (!visited.contains(node)) {
                Queue<Integer> queue = new LinkedList<>();
                visited.add(node);
                queue.offer(node);
                int currCities = 0;

                while (!queue.isEmpty()) {
                    int curr = queue.poll();
                    currCities++;
                    for (int neighbour : graph.get(curr)) {
                        if (!visited.contains(neighbour)) {
                            visited.add(neighbour);
                            queue.offer(neighbour);
                        }
                    }
                }
                maxCities = Math.max(currCities, maxCities);
            }
        }
        return maxCities;
    }

    public static void main(String[] args) {
        int[] A = {1, 1, 1};
        int[] B = {1, 1, 1};

        System.out.println(maxCities(A, B));
    }
}
