package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class which checks the graph's methods
 *
 * @author Rotem Halbreich
 */

class WGraph_DSTest {
    private weighted_graph arrow, big, single, empty;

    @BeforeEach
    void beforeEach() {
        arrow = new WGraph_DS();
        big = new WGraph_DS();
        single = new WGraph_DS();
        empty = new WGraph_DS();

        for (int i = 0; i <= 7; i++) {
            arrow.addNode(i);
            big.addNode(i);
        }

        single.addNode(0);

        //Arrow graph:
        arrow.connect(0, 1, 1.0);
        arrow.connect(1, 2, 2.0);
        arrow.connect(2, 0, 3.0);
        arrow.connect(2, 3, 4.0);

        arrow.connect(4, 5, 5.0);
        arrow.connect(5, 6, 6.0);
        arrow.connect(6, 7, 7.0);
        arrow.connect(5, 7, 8.0);

        //Big graph:
        big.connect(0, 1, 0);
        big.connect(0, 7, 0);
        big.connect(0, 4, 0);
        big.connect(1, 7, 0);
        big.connect(1, 2, 0);
        big.connect(1, 3, 0);
        big.connect(2, 6, 0);
        big.connect(2, 3, 0);
        big.connect(3, 4, 0);
        big.connect(3, 5, 0);
        big.connect(4, 5, 0);
        big.connect(5, 6, 0);
        big.connect(5, 7, 0);
        big.connect(6, 7, 0);

    }

    @Test
    void getNode() {
        weighted_graph w = new WGraph_DS();
        w.addNode(0);
        weighted_graph w1 = new WGraph_DS();
        w1.addNode(0);
        assertEquals(w, w1);
    }

    @Test
    void hasEdge() {
        assertTrue(arrow.hasEdge(2, 0));
        assertFalse(arrow.hasEdge(4, 3));
        arrow.connect(3, 4, 10.0);
        assertTrue(arrow.hasEdge(4, 3));
        assertFalse(single.hasEdge(0, 0));


    }

    @Test
    void getEdge() {
        assertEquals(4, arrow.getEdge(3, 2));
        assertEquals(-1, arrow.getEdge(3, 4));
        assertNotEquals(0, arrow.getEdge(3, 4));
    }

    @Test
    void addNode() {
        assertEquals(8, arrow.nodeSize());
        arrow.addNode(8);
        assertEquals(9, arrow.nodeSize());
        assertEquals(9, arrow.getV().size());
        assertEquals(0, empty.nodeSize());
        empty.addNode(345);
        empty.addNode(45);
        assertEquals(2, empty.nodeSize());
    }

    @Test
    void connect() {
        assertEquals(-1, arrow.getEdge(2, 4));
        assertFalse(arrow.hasEdge(2, 4));
        arrow.connect(2, 4, 10);
        assertTrue(arrow.hasEdge(2, 4));
        assertEquals(10, arrow.getEdge(2, 4));
        double exp = arrow.edgeSize();
        arrow.connect(2, 3, 5.987543);
        assertEquals(exp, arrow.edgeSize());
    }

    @Test
    void getV() {
        assertEquals(big.getV().size(), big.nodeSize());
        big.removeEdge(0, 1);
        assertEquals(big.getV().size(), big.nodeSize());
        big.removeNode(0);
        assertEquals(big.getV().size(), big.nodeSize());
        assertNotEquals(big.getV().size(), arrow.getV().size());
        big.addNode(0);
        assertEquals(big.getV().size(), arrow.getV().size());
    }

    @Test
    void testGetV() {
        assertEquals(3, arrow.getV(2).size());
        arrow.removeEdge(2, 3);
        assertEquals(2, arrow.getV(2).size());
    }

    @Test
    void removeNode() {
        assertEquals(arrow.nodeSize(), big.nodeSize());
        big.removeNode(7);
        assertNotEquals(arrow.nodeSize(), big.nodeSize());
    }

    @Test
    void removeEdge() {
        assertEquals(14, big.edgeSize());
        big.removeEdge(0, 1);
        assertEquals(13, big.edgeSize());
        big.removeEdge(2, 1);
        assertEquals(12, big.edgeSize());
        assertEquals(0, single.edgeSize());
        single.removeEdge(0, 0);
        assertEquals(0, single.edgeSize());
        assertNotEquals(3.4, single.edgeSize());
    }

    @Test
    void nodeSize() {
        assertEquals(big.nodeSize(), arrow.nodeSize());
        assertNotEquals(single.nodeSize(), arrow.nodeSize());
        assertEquals(1, single.nodeSize());
        single.addNode(5);
        assertEquals(2, single.getV().size());

    }

    @Test
    void edgeSize() {
        assertEquals(8, arrow.edgeSize());
        arrow.connect(3, 4, 200.0);
        assertEquals(9, arrow.edgeSize());
        assertFalse(single.hasEdge(0, 1));
        assertEquals(-1, single.getEdge(0, 200));
        assertFalse(single.hasEdge(0, 5));
        single.addNode(5);
        single.connect(0, 5, 56.0);
        assertEquals(56.0, single.getEdge(0, 5));
    }

    @Test
    void getMC() {
        assertNotEquals(arrow.getMC(), single.getMC());
        assertNotEquals(big.getMC(), arrow.getMC());
        arrow.addNode(10);
        arrow.addNode(11);
        arrow.addNode(12);
        arrow.addNode(23);
        arrow.addNode(13);
        arrow.addNode(124);
        assertEquals(big.getMC(), arrow.getMC());
        assertEquals(22, arrow.getMC());
        arrow.removeNode(10);
        arrow.addNode(10);
        assertEquals(24, arrow.getMC());
    }

    @Test
    void testToString() {
        single.addNode(123);
        single.connect(0, 123, 34.7);
        single.addNode(6);
        single.connect(6, 123, 200.0);
        assertEquals("Ver: [0, 6, 123]\n" +
                "[{0,123;34.7}, {6,123;200.0}, {123,0;34.7}, {123,6;200.0}]", single.toString());
    }
}