/**
 *This program analyzes the data and determines if the patrol path is valid.
 It does this by reading from two text files to generate a map and check if the path is valid.
 Finally, after sorted by path weight or alphabetically(if tied) the results are written to patrols.txt
 * @author Senay Gebru || NETID: STG230001
 * Date: 11/26/2023
 */
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Initialize a scanner to read user input from the console
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the name of the file containing the galaxy map
        System.out.print("Enter the name of the file containing the map of the galaxy: ");
        String galaxyMapFileName = scanner.nextLine();

        // Ask the user to enter the name of the file containing the pilot routes
        System.out.print("Enter the name of the file containing the pilot routes: ");
        String pilotRoutesFileName = scanner.nextLine();

        // Close the scanner as it's no longer needed
        scanner.close();

        // Create an instance of Graph to represent the galaxy map
        Graph galaxyGraph = new Graph();

        // Read the galaxy map data from the file and populate the graph
        readGalaxyMapFile(galaxyGraph, galaxyMapFileName);

        // Read the pilot routes from the file and analyze them
        List<PilotRouteResult> results = analyzePilotRoutes(galaxyGraph, pilotRoutesFileName);

        // Write the analysis results of pilot routes to the output file
        writeAnalysisResults(results);
    }

    /**
     * Reads the galaxy map file and populates the graph with its data.
     * @param galaxyGraph The graph object representing the galaxy map.
     * @param galaxyMapFileName The name of the galaxy map file.
     */
    private static void readGalaxyMapFile(Graph galaxyGraph, String galaxyMapFileName) {
        try {
            // Open the file and set up a scanner to read its contents
            File file = new File(galaxyMapFileName);
            Scanner fileScanner = new Scanner(file);

            // Read each line of the file, representing connections in the galaxy
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("[ ,]+"); // Regular expression to split by spaces and/or commas 
                String vertex = parts[0];
                String adjacentVertex = parts[1];
                int weight = Integer.parseInt(parts[2]);

                // Insert the connection (edge) into the graph
                galaxyGraph.insert(vertex, adjacentVertex, weight);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + galaxyMapFileName);
        }
    }

    /**
     * Analyzes the pilot routes using the galaxy graph and returns the results.
     * @param galaxyGraph The graph object representing the galaxy map.
     * @param pilotRoutesFileName The name of the file containing pilot routes.
     * @return A list of results for each pilot's route analysis.
     */
    private static List<PilotRouteResult> analyzePilotRoutes(Graph galaxyGraph, String pilotRoutesFileName) {
        List<PilotRouteResult> results = new ArrayList<>();
        try {
            // Open the file and set up a scanner to read its contents
            File file = new File(pilotRoutesFileName);
            Scanner fileScanner = new Scanner(file);

            // Read each line of the file, representing a pilot's route
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" ");
                String pilotName = parts[0];
                List<String> path = Arrays.asList(Arrays.copyOfRange(parts, 1, parts.length));

                // Check if the path is valid and calculate its weight
                boolean isValid = galaxyGraph.isValidPath(path);
                int pathWeight = isValid ? calculatePathWeight(galaxyGraph, path) : 0;

                // Add the result of the analysis to the list
                results.add(new PilotRouteResult(pilotName, pathWeight, isValid));
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + pilotRoutesFileName);
        }
        return results;
    }

    /**
     * Calculates the total weight of a given path in the galaxy graph.
     * @param galaxyGraph The graph object representing the galaxy map.
     * @param path A list of vertices representing the path.
     * @return The total weight of the path.
     */
    private static int calculatePathWeight(Graph galaxyGraph, List<String> path) {
        int totalWeight = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String startVertex = path.get(i);
            String endVertex = path.get(i + 1);

            // Get the weight of the edge between the consecutive vertices
            int edgeWeight = galaxyGraph.getEdgeWeight(startVertex, endVertex);
            totalWeight += edgeWeight;
        }
        return totalWeight;
    }

    /**
     * Writes the analysis results of pilot routes to an output file.
     * @param results The list of pilot route analysis results.
     */
    private static void writeAnalysisResults(List<PilotRouteResult> results) {
        // Sort the results based on path weight and pilot name in case of a tie
        Collections.sort(results, new Comparator<PilotRouteResult>() {
            @Override
            public int compare(PilotRouteResult o1, PilotRouteResult o2) {
                if (o1.pathWeight != o2.pathWeight) {
                    return Integer.compare(o1.pathWeight, o2.pathWeight);
                }
                return o1.pilotName.compareTo(o2.pilotName);
            }
        });

        try {
            // Write the sorted results to an output file
            FileWriter fileWriter = new FileWriter("patrols.txt");
            for (PilotRouteResult result : results) {
                fileWriter.write(result.pilotName + " " + result.pathWeight + " " + (result.isValid ? "Valid" : "Invalid") + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: patrols.txt");
        }
    }

    /**
     * Inner class to store the results of a pilot route analysis.
     */
    private static class PilotRouteResult {
        String pilotName;
        int pathWeight;
        boolean isValid;

        public PilotRouteResult(String pilotName, int pathWeight, boolean isValid) {
            this.pilotName = pilotName;
            this.pathWeight = pathWeight;
            this.isValid = isValid;
        }
    }
}
