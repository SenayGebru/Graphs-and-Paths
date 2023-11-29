import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Graph {
    /**
     * Inner class representing an edge in the graph.
     * Each edge has a destination vertex and a weight.
     */
    private class Edge {
        String destination;
        int weight;

        // Constructor for Edge
        public Edge(String destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    // A map that associates each vertex with its list of outgoing edges
    private Map<String, List<Edge>> adjacencyList;

    // Constructor to initialize the graph's adjacency list
    public Graph() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Inserts a new edge into the graph. In an undirected graph,
     * an edge is added in both directions.
     * @param vertex The starting vertex of the edge.
     * @param adjacentVertex The destination vertex of the edge.
     * @param weight The weight of the edge.
     */
    public void insert(String vertex, String adjacentVertex, int weight) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
        adjacencyList.putIfAbsent(adjacentVertex, new ArrayList<>());
        adjacencyList.get(vertex).add(new Edge(adjacentVertex, weight));
        adjacencyList.get(adjacentVertex).add(new Edge(vertex, weight)); // Add edge in opposite direction for undirected graph
    }

    /**
     * Checks if a given path is valid in the graph.
     * A valid path has a direct edge between each pair of consecutive vertices.
     * @param path A list of vertices representing the path.
     * @return true if the path is valid, false otherwise.
     */
    public boolean isValidPath(List<String> path) {
        if (path == null || path.size() < 2) {
            return false; // A path needs at least a start and an end point to be valid
        }

        String startVertex = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            String endVertex = path.get(i);
            // Check for the existence of a direct edge between the start and end vertices
            if (!isDirectEdge(startVertex, endVertex)) {
                return false; // If any direct edge is missing, the path is invalid
            }
            startVertex = endVertex; // Move to the next pair of vertices in the path
        }
        return true; // The path is valid if all direct edges exist
    }

    /**
     * Helper method to check if a direct edge exists between two vertices.
     * @param startVertex The starting vertex.
     * @param endVertex The ending vertex.
     * @return true if a direct edge exists, false otherwise.
     */
    private boolean isDirectEdge(String startVertex, String endVertex) {
        if (!adjacencyList.containsKey(startVertex)) {
            return false;
        }
        for (Edge edge : adjacencyList.get(startVertex)) {
            if (edge.destination.equals(endVertex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the weight of an edge between two vertices. In an undirected graph,
     * this method checks for the edge in both directions.
     * @param startVertex The starting vertex.
     * @param endVertex The ending vertex.
     * @return The weight of the edge if it exists, 0 otherwise.
     */
    public int getEdgeWeight(String startVertex, String endVertex) {
        if (!adjacencyList.containsKey(startVertex)) {
            return 0;
        }
        for (Edge edge : adjacencyList.get(startVertex)) {
            if (edge.destination.equals(endVertex)) {
                return edge.weight;
            }
        }
        // Check for edge in the opposite direction in case of undirected graph
        if (adjacencyList.containsKey(endVertex)) {
            for (Edge edge : adjacencyList.get(endVertex)) {
                if (edge.destination.equals(startVertex)) {
                    return edge.weight;
                }
            }
        }
        return 0;
    }
}
