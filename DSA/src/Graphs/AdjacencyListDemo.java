package Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListDemo {
    private final Map<Integer, List<Integer>> graph;

    public AdjacencyListDemo() {
        this.graph = new HashMap<>();
    }

    private void addVertex(int vertex) {
        graph.putIfAbsent(vertex, new ArrayList<>());
    }

    void addEdge(int src, int dest) {
        addVertex(src);
        addVertex(dest);
        if (!graph.get(src).contains(dest)) graph.get(src).add(dest);
        if (!graph.get(dest).contains(src)) graph.get(dest).add(src);
    }

    void removeEdge(int src, int dest) {
        List<Integer> srcList = graph.get(src);
        List<Integer> destList = graph.get(dest);
        if (srcList != null) srcList.remove((Integer) dest);
        if (destList != null) destList.remove((Integer) src);
    }

    void removeVertex(int vertex) {
        if (!graph.containsKey(vertex)) return;

        for (List<Integer> list : graph.values()) {
            list.remove((Integer) vertex);
        }
        graph.remove(vertex);
    }

    public void printGraph() {
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public Map<Integer, List<Integer>> getGraph() {
        return graph;
    }
}
