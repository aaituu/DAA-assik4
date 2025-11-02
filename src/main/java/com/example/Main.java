package com.example;


import com.example.dagsp.DagPaths;
import com.example.scc.CondensationGraph;
import com.example.scc.SccFinder;
import com.example.topo.TopoSort;
import com.example.util.GraphLoader;
import com.example.util.GraphLoader.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String filePath = "src/main/data/tasks.json";
            GraphData data = GraphLoader.loadGraph(filePath);

            System.out.println("----Graph ingo----");
            System.out.println("nodes: " + data.n);
            System.out.println("Edges: " + data.edges.size());
            System.out.println();

            List<List<Integer>> adj = GraphLoader.buildAdjList(data.n, data.edges);

            System.out.println("----Step 1----");
            SccFinder sccFinder = new SccFinder(data.n, adj);
            List<List<Integer>> sccs = sccFinder.findSCCs();

            System.out.println("Number os SCCs: " + sccs.size());
            for (int i = 0; i < sccs.size(); i++) {
                System.out.println("SCC " + i + ": " + sccs.get(i) + " (size: " + sccs.get(i).size() + ")");
            }
            System.out.println();

            System.out.println("----Step 2----");
            CondensationGraph condensation = new CondensationGraph(data.n, sccs, data.edges);
            List<int[]> condEdges = condensation.getCondensationGraph();

            System.out.println("Condensation graph has " + condensation.numComponents + "nodes");
            System.out.println("Condensation edges: ");
            for (int[] edge : condEdges) {
                System.out.println(" " + edge[0] + " -> " + edge[1]);
            }
            System.out.println();

            System.out.println("----Step 3 ropological sort----");
            TopoSort topoSort = new TopoSort(condensation.numComponents, condensation.adj);
            List<Integer> topoOrder = topoSort.khanSort();

            if (topoOrder == null) {
                System.out.println("Error: Condensation graph has cycle");
                return;
            }

            System.out.println("Topological order of components: " + topoOrder);

            System.out.println("Task order (by comp)");
            for (int comp : topoOrder) {
                System.out.println("Component " + comp + ": " + sccs.get(comp));
            }
            System.out.println();

            System.out.println("----Step 4  DAG(SHortest and Longest----");

            List<List<Edge>> weightedAdj = GraphLoader.buildWeightedAdjList(data.n, data.edges);

            TopoSort origTopoSort = new TopoSort(data.n, adj);
            List<Integer> origTopoOrder = origTopoSort.khanSort();

            if (origTopoOrder == null) {
                System.out.println("Original graph has cycle");
                origTopoOrder = new ArrayList<>();
                for (int comp : topoOrder) {
                    if (!sccs.get(comp).isEmpty()) {
                        origTopoOrder.add(sccs.get(comp).get(0));
                    }
                }
            }

            int source = 0;
            if (data.source >= 0 && data.source < data.n) {
                source = data.source;
            }
            System.out.println("Using source node: " + source);

            DagPaths dagPaths = new DagPaths(data.n, weightedAdj, origTopoOrder);


            int[] shortestDist = dagPaths.shortestPaths(source);
            System.out.println("\nShortest distances from source " + source + ":");
            for (int i = 0; i < data.n; i++) {
                if (shortestDist[i] == Integer.MAX_VALUE) {
                    System.out.println("  Node " + i + ": unreachable");
                } else {
                    System.out.println("  Node " + i + ": " + shortestDist[i]);
                }
            }
            DagPaths.PathResult criticalPath = dagPaths.findCriticalPath(source);
            System.out.println("\nCritical path (longest path):");
            System.out.println("  Path: " + criticalPath.path);
            System.out.println("  Length: " + criticalPath.length);
        }catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
        }
    }
}