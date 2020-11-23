package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import ex1.src.*;
import org.junit.jupiter.api.*;

import java.util.LinkedList;

/**
 * Test class which checks the graph's algorithms
 *
 * @author Rotem Halbreich
 */

class WGraph_AlgoTest {

    private weighted_graph_algorithms row, pentagon, arrow, big, single, empty;

    @BeforeEach
    void beforeEach() {
        weighted_graph r = new WGraph_DS();
        weighted_graph p = new WGraph_DS();
        weighted_graph a = new WGraph_DS();
        weighted_graph b = new WGraph_DS();
        weighted_graph s = new WGraph_DS();
        weighted_graph e = new WGraph_DS();

        for (int i = 0; i <= 7; i++) {
            b.addNode(i);
            a.addNode(i);
        }

        //Arrow graph:
        a.connect(0, 1, 1.0);
        a.connect(1, 2, 2.0);
        a.connect(2, 0, 3.0);
        a.connect(2, 3, 4.0);

        a.connect(4, 5, 5.0);
        a.connect(5, 6, 6.0);
        a.connect(6, 7, 7.0);
        a.connect(5, 7, 8.0);

        //Big graph:
        b.connect(0, 1, 0);
        b.connect(0, 7, 0);
        b.connect(0, 4, 0);
        b.connect(1, 7, 0);
        b.connect(1, 2, 0);
        b.connect(1, 3, 0);
        b.connect(2, 6, 0);
        b.connect(2, 3, 0);
        b.connect(3, 4, 0);
        b.connect(3, 5, 0);
        b.connect(4, 5, 0);
        b.connect(5, 6, 0);
        b.connect(5, 7, 0);
        b.connect(6, 7, 0);

        // Empty graph:
        empty = new WGraph_Algo();

        // Single vertex graph:
        s.addNode(0);

        // 1M vertices in a row:
        for (int i = 0; i <= 1000000; i++) {
            r.addNode(i);
        }
        for (int j = 0; j < 1000000; j++) {
            r.connect(j, j + 1, j);
        }

        //Initialization of Pentagon:
        for (int i = 0; i < 5; i++) {
            p.addNode(i);
        }
        p.connect(0, 1, 1.0);
        p.connect(1, 2, 1.0);
        p.connect(2, 3, 1.0);
        p.connect(4, 3, 1.0);
        p.connect(0, 4, 1.0);

        p.connect(0, 2, 45.3);
        p.connect(0, 3, 100.0);
        p.connect(1, 3, 7.0);
        p.connect(4, 1, 0.0);
        p.connect(2, 4, 200.0);

        row = new WGraph_Algo(r);
        pentagon = new WGraph_Algo(p);
        arrow = new WGraph_Algo(a);
        big = new WGraph_Algo(b);
        single = new WGraph_Algo(s);
        empty = new WGraph_Algo(e);
    }

    @Test
    void init() {
        weighted_graph_algorithms w = new WGraph_Algo();
        w.init(arrow.getGraph());
        assertEquals(arrow, w);

    }

    @Test
    void getGraph() {
        assertEquals(empty.getGraph(), new WGraph_DS());
    }

    @Test
    void copy() {
        weighted_graph a = arrow.copy();
        assertEquals(arrow, a);
        a.addNode(58);
        assertNotEquals(arrow, a);
        a.removeNode(58);
        assertEquals(arrow, a);
    }

    @Test
    void isConnected() {
        assertFalse(arrow.isConnected());
        assertTrue(big.isConnected());
        assertTrue(single.isConnected());
        assertTrue(empty.isConnected());

        assertTrue(row.isConnected());
        row.getGraph().removeNode(500);
        assertFalse(row.isConnected());
        row.getGraph().addNode(500);
        row.getGraph().connect(499, 500, 2.0);
        row.getGraph().connect(500, 501, 45.7);
        assertTrue(row.isConnected());

        assertTrue(pentagon.isConnected());
        pentagon.getGraph().removeEdge(0, 4);
        assertTrue(pentagon.isConnected());
    }

    @Test
    void shortestPathDist() {
        assertEquals(2.0, pentagon.shortestPathDist(0, 3));
        assertEquals(0.0, pentagon.shortestPathDist(4, 1));
        assertEquals(0.0, pentagon.shortestPathDist(3, 3));
        assertEquals(13.0, arrow.shortestPathDist(7, 4));
    }

    @Test
    void shortestPath() {
        LinkedList<node_info> list = new LinkedList<>();
        list.add(arrow.getGraph().getNode(0));
        list.add(arrow.getGraph().getNode(2));
        list.add(arrow.getGraph().getNode(3));
        assertEquals(list, arrow.shortestPath(0, 3));

        list.removeAll(list);
        list.add(arrow.getGraph().getNode(4));
        list.add(arrow.getGraph().getNode(5));
        list.add(arrow.getGraph().getNode(6));
        assertEquals(list, arrow.shortestPath(4, 6));

    }

    @Test
    void saveAndLoad() {
        weighted_graph_algorithms text = new WGraph_Algo();
        arrow.save("myGraph.txt");
        text.load("myGraph.txt");
        assertEquals(arrow, text);

        empty.save("myGraph.txt");
        text.load("myGraph.txt");
        assertEquals(empty, text);

        big.save("myGraph.txt");
        text.load("myGraph.txt");
        assertEquals(big, text);
    }
}