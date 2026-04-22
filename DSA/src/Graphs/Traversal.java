package Graphs;

import java.util.*;

public class Traversal {
    public void DfsIterative(int startVertex, List<Integer> result, Map<Integer, List<Integer>> graph){
        Stack<Integer> stack = new Stack<>();
        Set<Integer> set = new HashSet<>();
        stack.push(startVertex);
        while (!stack.isEmpty()){
            int curr = stack.pop();
            if(set.contains(curr)) continue;
            result.add(curr);
            set.add(curr);
            List<Integer> neighbors = graph.getOrDefault(curr, Collections.emptyList());

            for (int i = neighbors.size() - 1; i >= 0; i--) {
                int neigh = neighbors.get(i);
                if (!set.contains(neigh)) {
                    stack.push(neigh);
                }
            }
        }
    }
    public void dfsRecursive(int startVertex, Map<Integer, List<Integer>> graph){
        Set<Integer> set = new HashSet<>();
        dfs(startVertex, set, graph);
        System.out.println();
    }
    private void dfs(int vertex, Set<Integer> set, Map<Integer, List<Integer>> graph){
        set.add(vertex);
        System.out.print(vertex + " ");
        for(int neighbour: graph.getOrDefault(vertex, Collections.emptyList())){
            if(!set.contains(neighbour)){
                dfs(neighbour, set, graph);
            }
        }
    }
    public void bfsIterative(int start, Map<Integer, List<Integer>> graph){
        Queue<Integer> q = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        q.offer(start);
        visited.add(start);
        while (!q.isEmpty()){
            int curr = q.poll();
            System.out.print(curr + " ");
            for( int neighbour: graph.getOrDefault(curr, Collections.emptyList())){
                if(!visited.contains(neighbour)){
                    visited.add(neighbour);
                    q.offer(neighbour);
                }
            }
        }
    }

}
