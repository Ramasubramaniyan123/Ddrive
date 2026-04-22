package Graphs;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyListMain {
    public static void main(String[] args) {
        AdjacencyListDemo g = new AdjacencyListDemo();

        // Add edges (auto-creates vertices)
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 5);

        System.out.println("Graph after adding edges:");
        g.printGraph();
//        Traversal t = new Traversal();
//        List<Integer> res = new ArrayList<>();
//        //t.DfsIterative(1, res, g.getGraph());
//        System.out.println(res);
        // Remove edge
        g.removeEdge(1, 3);
        System.out.println("\nAfter removing edge (1,3):");
        g.printGraph();

        // Remove vertex
        g.removeVertex(2);
        System.out.println("\nAfter removing vertex 2:");
        g.printGraph();

        // Try invalid operations (won’t crash)
        g.removeEdge(10, 20);
        g.removeVertex(100);

    }
}
