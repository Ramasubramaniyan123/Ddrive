package Graphs;
import java.util.*;
public class TraversalDemo {
    public static void main(String[] args) {

        // 🔹 Build graph
        AdjacencyListDemo g = new AdjacencyListDemo();

        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 6);
        g.addEdge(3, 7);

        System.out.println("Graph:");
        g.printGraph();

        Traversal t = new Traversal();

        // 🔹 Recursive DFS
        System.out.print("\nDFS Recursive (start = 1): ");
        t.dfsRecursive(1, g.getGraph());

        // 🔹 Iterative DFS
        List<Integer> res = new ArrayList<>();
        t.DfsIterative(1, res,  g.getGraph());
        System.out.println("DFS Iterative (start = 1): "  + res);
    }
}
