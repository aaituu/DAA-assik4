package com.example.scc;

import com.example.util.GraphLoader.Edge;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CondensationGraph {
    public int numComponents;
    public List<List<Integer>> adj;
    public int[] nodeToComponent;

    public CondensationGraph(int n,List<List<Integer>> sccs, List<Edge> originalEdges) {
        this.numComponents = sccs.size();
        this.nodeToComponent = new int[n];

        for (int i = 0; i < sccs.size(); i++) {
            for (int node : sccs.get(i)) {
                nodeToComponent[node] = 1;
            }
        }

        adj = new ArrayList<>();
        for (int i = 0; i< numComponents; i++) {
            adj.add(new ArrayList<>());
        }

        Set<String> edgeSet = new HashSet<>();

        for (Edge e : originalEdges){
            int compU  = nodeToComponent[e.u];
            int compV = nodeToComponent[e.v];

            if (compU != compV){
                String edgeKey = compU+"->"+compV;
                if  (!edgeSet.contains(edgeKey)){
                    adj.get(compU).add(compV);
                    edgeSet.add(edgeKey);
                }
            }
        }
    }

    public List<int[]> getCondensationGraph() {
        List<int[]> edges = new ArrayList<>();
        for (int u = 0; u< numComponents; u++){
            for(int v: adj.get(u)){
                edges.add(new int[]{u,v});
            }
        }
        return edges;
    }
}
