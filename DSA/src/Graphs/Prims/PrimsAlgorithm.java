package Graphs.Prims;

import java.util.*;

public class PrimsAlgorithm {
    public void prims(int start, Map<Integer, List<Edge>> graph) {
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        visited.add(start);
        queue.addAll(graph.getOrDefault(start, Collections.emptyList()));
        System.out.println("Minimum Spanning Tree Edges");
        int totalCost = 0;
        while (!queue.isEmpty()) {
            Edge curr = queue.poll();
            int destination = curr.getDestination();
            if (visited.contains(destination)) continue;

            visited.add(destination);
            totalCost += curr.getWeight();
            System.out.println(
                    curr.getSource() + " -> " +
                            destination + " (w=" + curr.getWeight() + ")"
            );
            for (Edge next : graph.getOrDefault(destination, Collections.emptyList())) {
                if (!visited.contains(next.getDestination())) {
                    queue.offer(next);
                }
            }
        }
        System.out.println("Total Cost = " + totalCost);
    }
}
