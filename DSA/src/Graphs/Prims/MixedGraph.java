package Graphs.Prims;

import java.util.*;

public class MixedGraph {

    private final Map<Integer, List<Edge>> graph;

    public MixedGraph() {
        this.graph = new HashMap<>();
    }

    // 🔹 ensure vertex exists
    private void addVertex(int v) {
        graph.putIfAbsent(v, new ArrayList<>());
    }

    // 🔹 Directed edge: u → v
    public void addDirectedEdge(int u, int v, int weight) {
        addVertex(u);
        addVertex(v);

        graph.get(u).add(new Edge(u, v, weight));
    }

    // 🔹 Undirected edge: u ↔ v
    public void addUndirectedEdge(int u, int v, int weight) {
        addVertex(u);
        addVertex(v);

        graph.get(u).add(new Edge(u, v, weight));
        graph.get(v).add(new Edge(v, u, weight));
    }

    // 🔹 Get neighbors
    public List<Edge> getNeighbors(int v) {
        return graph.getOrDefault(v, Collections.emptyList());
    }

    // 🔹 Needed for algorithms (Prim, Dijkstra, etc.)
    public Map<Integer, List<Edge>> getGraphMap() {
        return graph;
    }

    // 🔹 Print graph
    public void printGraph() {
        for (Map.Entry<Integer, List<Edge>> entry : graph.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Edge e : entry.getValue()) {
                System.out.print(
                        "(" + e.getSource() +
                                "->" + e.getDestination() +
                                ", w=" + e.getWeight() + ") "
                );
            }
            System.out.println();
        }
    }
}