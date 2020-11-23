package ex1.src;

import java.io.*;
import java.util.*;

/**
 * This class represents an Undirected (positive) Weighted "Graph Theory" algorithms including methods:
 * 0. clone() - Deep copy of a graph
 * 1. init(graph) - Initializes the graph
 * 2. isConnected() - Checks if all the vertices are connected to each other by edges
 * 3. double shortestPathDist(int src, int dest) - Checks the shortest path distance
 * 4. List<node_data> shortestPath(int src, int dest) - Returns the vertices' shortest
 * path route as an ordered LinkedList
 * 5. Save(file)
 * 6. Load(file)
 *
 * @author Rotem Halbreich
 */

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {

    private static final String UNVISITED = "white", VISITED = "gray", END_ROUND = "black";
    public weighted_graph g;

    public WGraph_Algo(weighted_graph g) {
        init(g);
    }

    public WGraph_Algo() {
        this.g = new WGraph_DS();
    }


    /**
     * Initialize the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        if (g != null) {
            this.g = g;
        }
    }

    /**
     * Return the underlying graph of which this class works on.
     *
     * @return weighted undirected graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.g;
    }

    /**
     * Computes a deep copy of this graph.
     *
     * @return a new similar graph (which isn't depended on the first graph)
     */
    @Override
    public weighted_graph copy() {
        weighted_graph ans = new WGraph_DS();
        for (node_info n : g.getV()) {
            ans.addNode(n.getKey());
            for (node_info ni : g.getV(n.getKey())) {
                ans.connect(n.getKey(), ni.getKey(), g.getEdge(n.getKey(), ni.getKey()));
            }
        }
        return ans;
    }

    /**
     * Checks if there's a valid path between all vertices (aka connected graph).
     * in this method there's a usage of BFS algorithm in order to determine whether the
     * graph is connected by edges or not.
     * I've got the main idea about the BFS algorithm using this video:
     * https://www.youtube.com/watch?v=TIbUeeksXcI&feature=youtu.be&ab_channel=BackToBackSWE
     *
     * @return boolean (true/false)
     */
    @Override
    public boolean isConnected() {
        if (g.nodeSize() <= 1) return true;
        if (g.edgeSize() < g.nodeSize() - 1) return false;
        node_info iter = this.g.getV().iterator().next();
        BFS(iter);
        // For every node, if the info isn't "END_ROUND"
        // we can determine the graph isn't connected
        for (node_info node : this.g.getV()) {
            if (!node.getInfo().equals(END_ROUND))
                return false;
        }
        return true;
    }

    /**
     * Help function BFS algorithm: passes through all the vertices in the graph
     * from src to dest. Pushes a starting vertex to the queue and then
     * goes through all his neighbors which are "UNVISITED" and changes them
     * to "VISITED", removes from the current vertex from the queue,
     * then pushes the current neighbor to the queue. and goes on.
     *
     * @param start
     * @return boolean
     */
    private boolean BFS(node_info start) {
        Queue<node_info> queue = new LinkedList<>();
        queue.add(start);

        // Sets every node's info as "UNVISITED"
        for (node_info vertices : g.getV()) {
            vertices.setInfo(UNVISITED);
        }
        // For every new vertex in the queue, we set it's info from "UNVISITED" to "VISITED"
        while (!queue.isEmpty()) {
            node_info current = queue.poll();
            if (current.getInfo().equals(UNVISITED)) {
                current.setInfo(VISITED);
            }
            Collection<node_info> current_Ni = g.getV(current.getKey());
            // Sets this vertex's neighbors info to "VISITED"
            // and adds all the neighbors of this vertex to the Queue
            for (node_info neighbor : current_Ni) {
                if (neighbor.getInfo().equals(UNVISITED)) {
                    neighbor.setInfo(VISITED);
                    queue.add(neighbor);
                }
            }
            current.setInfo(END_ROUND);
        }
        return true;
    }

    /**
     * Returns the length of the shortest path between src to dest vertices.
     * In this method there's a usage of Dijkstra's algorithm (see below).
     * If no such path --> returns -1
     * If one of the vertices (src/dest) doesn't exist --> Throw RuntimeException
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return double
     */
    @Override
    public double shortestPathDist(int src, int dest) {

        if (g.getNode(src) == null || g.getNode(dest) == null) return -1;
        Dijkstra((WGraph_DS) g, g.getNode(src), dest);
        double a = g.getNode(dest).getTag();
        if (g.getNode(dest).getTag() == Double.MAX_VALUE) g.getNode(dest).setTag(-1);
        return g.getNode(dest).getTag();
    }

    /**
     * returns the the shortest path route between src to dest - as an ordered List of nodes:
     * (src)--> (n1)--> (n2)--> ...-->(dest)
     * In this method there's a usage of Dijkstra's algorithm (see below).
     * If no such path --> returns null;
     * If one of the vertices (src/dest) doesn't exist --> Throw RuntimeException
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return LinkedList
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        HashMap<Integer, node_info> hash = Dijkstra((WGraph_DS) g, g.getNode(src), dest);
        if (g.getNode(src) == null || g.getNode(dest) == null) return null;
        if (g.getNode(dest).getTag() == Double.MAX_VALUE) return null;
        boolean flag = true;
        LinkedList<node_info> l = new LinkedList<>();
        l.add(g.getNode(dest));
        if (src == dest) return l;
        while (flag) {
            node_info n = hash.get(dest);
            if (n.getKey() == src) {
                flag = false;
            }
            l.addFirst(n);
            dest = n.getKey();
        }
        return l;
    }

    /**
     * Help function, Dijkstra algorithm:
     * Goes through all the vertices and checks the shortest path there is
     * to any neighbor, if there is such path --> changes the tag to the shortest (weight)
     * then pushes it to the queue.
     * At last returns a HashMap representing shortestPath.
     *
     * If one of the vertices (src/dest) doesn't exist --> Throw RuntimeException
     *
     * @param g
     * @param src
     * @param dest
     * @return HashMap
     */
    private HashMap<Integer, node_info> Dijkstra(WGraph_DS g, node_info src, int dest) {
        HashMap<Integer, node_info> prevNodes = new HashMap<>();
        PriorityQueue<node_info> PQ = new PriorityQueue<>();
        if (g.getNode(dest) == null || src == null) throw new RuntimeException("Invalid value");

        src.setTag(0.0);
        src.setInfo(UNVISITED);
        PQ.add(src);

        for (node_info vertex : g.getV()) {
            if (vertex != src) {
                vertex.setTag(Double.MAX_VALUE);
                vertex.setInfo(UNVISITED);
            }
        }
        while (!PQ.isEmpty()) {
            node_info curr = PQ.poll();
            Collection<node_info> current_Ni = g.getV(curr.getKey());
            if (curr.getKey() == dest || curr.getTag() == Double.MAX_VALUE) return prevNodes;
            for (node_info neighbor : current_Ni) {
                double currWeight = curr.getTag() + g.getEdge(neighbor.getKey(), curr.getKey());
                if (neighbor.getInfo().equals(UNVISITED) && currWeight < neighbor.getTag()) {
                    PQ.remove(neighbor);
                    neighbor.setTag(currWeight);
                    PQ.add(neighbor);
                    prevNodes.put(neighbor.getKey(), curr);
                }
            }
            curr.setInfo(VISITED);
        }
        return prevNodes;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.g);
            fileOutputStream.close();
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            this.g = (weighted_graph) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

            return false;
        }
        return true;
    }

    /**
     * Represents the graph algo as a string.
     *
     * @return String
     */
    @Override
    public String toString() {
        return "WGraph_Algo: " + g.toString();
    }

    /**
     * Checks whether the graphs are equal.
     * Uses the method equals of WGraph_DS.
     *
     * @param o
     * @return boolean (true/false)
     */
    @Override
    public boolean equals(Object o) {
        return g.equals(o);
    }

    /**
     * HashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(g);
    }
}

