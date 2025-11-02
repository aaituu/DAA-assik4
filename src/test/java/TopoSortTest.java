import com.example.topo.TopoSort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TopoSortTest {

    @Test
    public void testSimpleDAG() {
        // Graph: 0->1, 0->2, 1->3, 2->3
        int n = 4;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(1);
        adj.get(0).add(2);
        adj.get(1).add(3);
        adj.get(2).add(3);

        TopoSort sorter = new TopoSort(n, adj);
        List<Integer> order = sorter.khanSort();

        assertNotNull(order);
        assertEquals(4, order.size());

        // Check all nodes present
        Set<Integer> nodes = new HashSet<>(order);
        assertEquals(4, nodes.size());

        // Check ordering: 0 before 1,2 and 1,2 before 3
        int pos0 = order.indexOf(0);
        int pos1 = order.indexOf(1);
        int pos2 = order.indexOf(2);
        int pos3 = order.indexOf(3);

        assertTrue(pos0 < pos1);
        assertTrue(pos0 < pos2);
        assertTrue(pos1 < pos3);
        assertTrue(pos2 < pos3);
    }

    @Test
    public void testLinearDAG() {
        // Graph: 0->1->2->3
        int n = 4;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(3);

        TopoSort sorter = new TopoSort(n, adj);
        List<Integer> order = sorter.khanSort();

        assertNotNull(order);
        assertEquals(4, order.size());

        // Should be exactly 0,1,2,3
        for (int i = 0; i < 4; i++) {
            assertEquals(i, order.get(i));
        }
    }

    @Test
    public void testCycle() {
        // Graph: 0->1->2->0 (cycle)
        int n = 3;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(0);

        TopoSort sorter = new TopoSort(n, adj);
        List<Integer> order = sorter.khanSort();

        assertNull(order); // should return null for cycle
    }
}