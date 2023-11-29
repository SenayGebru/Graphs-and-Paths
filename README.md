Galaxy Map and Pilot Route Analyzer

Project Overview:
This project is a Java application designed to analyze TIE fighter pilot patrol routes in a simulated galaxy. It takes input from two text files: 
one describing a galaxy map with distances between planets, and another listing pilot routes. The application constructs a graph from the galaxy map, 
validates the routes based on the graph, calculates the total distance of each valid route, and outputs the results.

Features:
Graph Construction: Builds a representation of the galaxy map as an undirected graph.
Route Validation: Checks if a pilot's route is valid within the galaxy map.
Distance Calculation: Calculates the total distance of each valid route.
Result Sorting: Sorts pilots based on route distance and alphabetically in case of ties.
Output Generation: Outputs the results to a file, detailing each pilot's route validity and distance.


How to Use
1. Clone the Repository:


2. Input Files:
Place your galaxy map file and pilot routes file in the project directory.
Ensure they are formatted as specified in the 'Input File Format' section.
Running the Application:


3. Running the application:
Compile and run Main.java using a Java IDE or command line.
Follow the prompts to input the names of your galaxy map and pilot routes files.


4. Viewing Results:
Check the generated patrols.txt file in the project directory for the analysis results.


Input File Format-


Galaxy Map File (galaxyMap.txt): Each line should contain a planet name followed by its connected planets and distances, e.g., Planet1 Destination1,distance1 Destination2,distance2.
Pilot Routes File (pilotRoutes.txt): Each line should start with a pilot's name followed by the sequence of planets in their route, e.g., PilotName Planet1 Planet2 Planet3.


Technologies:
Java

Contributions:
Contributions to this project are welcome. Please follow the standard procedure:
1. Fork the repository.
2. Create a new branch.
3. Make your changes and commit them.
4. Submit a pull request with a comprehensive description of changes.
