import com.example.scc.SccFinder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SccFinderTest {

    @Test
    public void testSimpleCycle() {
        // Graph: 0->1->2->0 (one SCC)
        int n = 3;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(0);

        SccFinder finder = new SccFinder(n, adj);
        List<List<Integer>> sccs = finder.findSCCs();

        assertEquals(1, sccs.size());
        assertEquals(3, sccs.get(0).size());
    }

    @Test
    public void testTwoSCCs() {
        // Graph: 0->1->2->1 and 3->4->3
        // Two SCCs: {1,2} and {3,4}
        int n = 5;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(1);
        adj.get(1).add(2);
        adj.get(2).add(1); // cycle 1-2
        adj.get(3).add(4);
        adj.get(4).add(3); // cycle 3-4

        SccFinder finder = new SccFinder(n, adj);
        List<List<Integer>> sccs = finder.findSCCs();

        assertEquals(3, sccs.size()); // {0}, {1,2}, {3,4}

        // Check all nodes are covered
        Set<Integer> allNodes = new HashSet<>();
        for (List<Integer> scc : sccs) {
            allNodes.addAll(scc);
        }
        assertEquals(5, allNodes.size());
    }

    @Test
    public void testDAG() {
        // Graph: 0->1->2 (no cycles, each node is own SCC)
        int n = 3;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        adj.get(0).add(1);
        adj.get(1).add(2);

        SccFinder finder = new SccFinder(n, adj);
        List<List<Integer>> sccs = finder.findSCCs();

        assertEquals(3, sccs.size());
        for (List<Integer> scc : sccs) {
            assertEquals(1, scc.size());
        }
    }
}