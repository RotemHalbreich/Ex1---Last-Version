package ex1.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ex1.src.*;
import static org.junit.jupiter.api.Assertions.*;

class AlgoRunTimeTest {

    private weighted_graph_algorithms huge = new WGraph_Algo();

    @BeforeEach
    void beforeEach(){
        weighted_graph h = new WGraph_DS();
        huge.init(h);

        // 1M vertices in a row:
        for (int i = 0; i <= 1000000; i++) {
            h.addNode(i);
        }
        for (int j = 0; j < 1000000; j++) {
            h.connect(j, j + 1, j);
        }
    }

    @Test
    void copy() {
        weighted_graph copy=huge.copy();
    }

    @Test
    void isConnected() {
        huge.isConnected();
    }

    @Test
    void shortestPathDist() {
        huge.shortestPathDist(0, 1000000);
    }

    @Test
    void shortestPath() {
        huge.shortestPath(0, 1000000);
    }

    @Test
    void saveAndLoad() {
        weighted_graph_algorithms text = new WGraph_Algo();
        huge.save("myGraph.txt");
        text.load("myGraph.txt");
        assertEquals(huge, text);
    }
}