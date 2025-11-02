import com.example.dagsp.DagPaths;
import org.junit.jupiter.api.Test;
import com.example.util.GraphLoader.Edge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DagPathsTest {

    @Test
    public void testShortestPath() {
        // Graph: 0->1(5), 0->2(3), 1->3(2), 2->3(4)
        // Shortest to 3: 0->2->3 = 7
        int n = 4;
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(new Edge(0, 1, 5));
        adj.get(0).add(new Edge(0, 2, 3));
        adj.get(1).add(new Edge(1, 3, 2));
        adj.get(2).add(new Edge(2, 3, 4));

        List<Integer> topoOrder = Arrays.asList(0, 1, 2, 3);

        DagPaths dagPaths = new DagPaths(n, adj, topoOrder);
        int[] dist = dagPaths.shortestPaths(0);

        assertEquals(0, dist[0]);
        assertEquals(5, dist[1]);
        assertEquals(3, dist[2]);
        assertEquals(7, dist[3]); // 0->2->3
    }

    @Test
    public void testLongestPath() {
        // Same graph: longest to 3 should be 0->1->3 = 7
        int n = 4;
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(new Edge(0, 1, 5));
        adj.get(0).add(new Edge(0, 2, 3));
        adj.get(1).add(new Edge(1, 3, 2));
        adj.get(2).add(new Edge(2, 3, 4));

        List<Integer> topoOrder = Arrays.asList(0, 1, 2, 3);

        DagPaths dagPaths = new DagPaths(n, adj, topoOrder);
        int[] dist = dagPaths.longestPaths(0);

        assertEquals(0, dist[0]);
        assertEquals(5, dist[1]);
        assertEquals(3, dist[2]);
        assertEquals(7, dist[3]); // 0->1->3 or 0->2->3 both = 7
    }

    @Test
    public void testCriticalPath() {
        // Linear graph: 0->1(3)->2(2)->3(5)
        // Critical path: 0->1->2->3 = 10
        int n = 4;
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(new Edge(0, 1, 3));
        adj.get(1).add(new Edge(1, 2, 2));
        adj.get(2).add(new Edge(2, 3, 5));

        List<Integer> topoOrder = Arrays.asList(0, 1, 2, 3);

        DagPaths dagPaths = new DagPaths(n, adj, topoOrder);
        DagPaths.PathResult result = dagPaths.findCriticalPath(0);

        assertEquals(10, result.length);
        assertEquals(Arrays.asList(0, 1, 2, 3), result.path);
    }

    @Test
    public void testUnreachableNode() {
        // Graph: 0->1, 2 is disconnected
        int n = 3;
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(new Edge(0, 1, 5));

        List<Integer> topoOrder = Arrays.asList(0, 2, 1);

        DagPaths dagPaths = new DagPaths(n, adj, topoOrder);
        int[] dist = dagPaths.shortestPaths(0);

        assertEquals(0, dist[0]);
        assertEquals(5, dist[1]);
        assertEquals(Integer.MAX_VALUE, dist[2]); // unreachable
    }
}