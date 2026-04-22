package Graphs.Prims;

public class PrimsMain {
    public static void main(String[] args) {

        MixedGraph g = new MixedGraph();

        // 🔹 Build UNDIRECTED weighted graph (required for Prim's)
        g.addUndirectedEdge(1, 2, 3);
        g.addUndirectedEdge(1, 3, 1);
        g.addUndirectedEdge(2, 3, 2);
        g.addUndirectedEdge(2, 4, 1);
        g.addUndirectedEdge(3, 4, 3);

        System.out.println("Graph:");
        g.printGraph();

        // 🔹 Run Prim's Algorithm
        PrimsAlgorithm prim = new PrimsAlgorithm();

        System.out.println("\nPrim's MST:");
        prim.prims(1, g.getGraphMap());
    }
}