# Assignment Ex1:

## Object Oriented Programming (Ariel University - Computer science department):

### This project deals with the subject of "Graph Theory" focusing on undirectional weighted graph.
#### The project assembeled of 3 different classes implemented from 3 different interfaces:

------------------------------------------------------------------------------------------
### NodeInfo:
------------------------------------------------------------------------------------------
An inner private class representing the graph's vertices:
	
Including methods:
- getKey() - Get a unique key ID for each vertex.
- getInfo() - Get the info of a vertex as a String, marking whether the vertex was "VISITED" or "UNVISITED".
- getTag() - Get the tag of a vertex marking the weight count of the previous vertices to the current vertex included.
- setTag() - Set the tag of a vertex.
- compareTo() - A function for tags comparison (weight).

------------------------------------------------------------------------------------------
### WGraph_DS:
------------------------------------------------------------------------------------------
Represents a graph assembeled of vertices:
In this class I chose using the HashMap data structure for its fast running time O(1).
My implementation is based on HashMap of vertices linked with a unique key for each vertex,
and HashMap of edges which links a vertex by a unique key to another vertex including
the weight of the edge between them, using HashMap inside HashMap.
	
Including methods:
- getNode() - Get a vertex by a unique key.
- hasEdge() - Check if there's an edge between two vertices.
- getEdge() - Gets an edge between two vertices represented by weight.
- addNode() - Adds a vertex to the graph.
- connect() - Connect between two vertices.
- getV() - Get the neighbors of the vertex as a collection.
- getV(node_id) - Get the value of the graph's vertices as a collection.
- removeNode() - Removes a vertex from the graph.
- removeEdge() - Removes an edge from the graph.
- nodeSize() - returns the number of vetices in the graph.
- edgeSize() - returns the number of edges in the graph.
- getMC() - returns the number of changes made to the graph.

------------------------------------------------------------------------------------------
### WGraph_Algo: 
------------------------------------------------------------------------------------------
Represents the algorithms we apply on the graph. 
In this class I use Dijkstra & BFS algorithms for implementing the algorithms below:
	
- init() - Initializes the graph.
- getGraph() - Returns an undirectional weighted graph.
- copy() - Computes a deep copy of the graph.
- isConnected() - Checks if all the vertices of the graph are connected by edges.
- shortestPathDist() - Returns the shortest path distance between two vertices of the graph (minimum weight). 
- shortestPath() - Returns the shortest path route between two vertices of the graph (minimum weight).
- save() - Saves a graph to a file.
- load() - Loads a graph from a file.
