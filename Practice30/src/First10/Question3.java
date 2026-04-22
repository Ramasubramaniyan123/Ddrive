package First10;

import java.util.*;

public class Question3 {
    public static int maxRoads(int [] a, int [] b){
        Map<Integer, List<Integer>> graph = new HashMap<>();

        Set<String> seen = new HashSet<>();

        for (int i = 0; i < a.length; i++) {
            int u = a[i], v = b[i];
            String key = Math.min(u,v) + "#" + Math.max(u,v);

            if (!seen.contains(key)) {
                graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
                graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
                seen.add(key);
            }
        }

        int maxRoads = 0;
        Set<Integer> visited = new HashSet<>();

        for(int node: graph.keySet()){
            if(!visited.contains(node)){
                Queue<Integer> queue = new LinkedList<>();
                visited.add(node);
                queue.offer(node);

                int degree = 0;
                while (!queue.isEmpty()){
                    int curr = queue.poll();
                    degree += graph.get(curr).size();

                    for(int neighbour: graph.get(curr)){
                        if(!visited.contains(neighbour)){
                            queue.offer(neighbour);
                            visited.add(neighbour);
                        }
                    }
                }
                maxRoads = Math.max(maxRoads, degree / 2);
            }
        }
        return maxRoads;
    }

    public static void main(String[] args) {
        int[] a = {1,2,3,4};
        int[] b = {4,3,2,1};
        System.out.println(maxRoads(a,b));
    }
}
