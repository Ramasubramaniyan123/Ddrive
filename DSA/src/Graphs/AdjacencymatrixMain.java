package Graphs;

public class AdjacencymatrixMain {
    public static void main(String[] args) {
        AdjacencyMatrixDemo graph = new AdjacencyMatrixDemo(4);
        graph.addEdges(0,1);
        graph.addEdges(1,2);
        graph.addEdges(2,3);
        graph.addEdges(3,0);
        graph.addEdges(0,2);
        graph.addEdges(1,3);
        graph.removeVertices(0);

        for(int i = 0;i < graph.numberOfVertices; i++){
            for(int j = 0;j < graph.numberOfVertices;j++){
                System.out.print(graph.adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
