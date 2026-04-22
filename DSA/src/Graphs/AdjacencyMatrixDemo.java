package Graphs;

public class AdjacencyMatrixDemo {
    int[][] adjacencyMatrix;
    int numberOfVertices;

    public AdjacencyMatrixDemo(int numberOfVertices) {
        this.adjacencyMatrix = new int[numberOfVertices][numberOfVertices];
        this.numberOfVertices = numberOfVertices;
    }

    public void addEdges(int i, int j) {
        adjacencyMatrix[i][j] = 1;
        adjacencyMatrix[j][i] = 1;
    }

    public void removeEdges(int i, int j) {
        adjacencyMatrix[i][j] = 0;
        adjacencyMatrix[j][i] = 0;
    }

    public void addVertices() {
        int[][] newAdjacencyMatrix = new int[numberOfVertices + 1][numberOfVertices + 1];
        for (int i = 0; i < numberOfVertices; i++)
            for (int j = 0; j < numberOfVertices; j++)
                newAdjacencyMatrix[i][j] = adjacencyMatrix[i][j];
        adjacencyMatrix = newAdjacencyMatrix;
        numberOfVertices++;
    }

    public void removeVertices(int vertices) {
        int[][] newAdjacencyMatrix = new int[numberOfVertices - 1][numberOfVertices - 1];
        for (int i = 0; i < numberOfVertices - 1; i++)
            for (int j = 0; j < numberOfVertices - 1; j++)
                if (i != vertices && j != vertices)
                    newAdjacencyMatrix[i][j] = adjacencyMatrix[i][j];

        adjacencyMatrix = newAdjacencyMatrix;
        numberOfVertices--;
    }
}
