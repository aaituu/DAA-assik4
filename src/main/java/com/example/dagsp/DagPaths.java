package com.example.dagsp;

import com.example.util.GraphLoader.Edge;

import java.util.ArrayList;
import java.util.List;

public class DagPaths {

    private int n;
    private List<List<Edge>> adj;
    private List<Integer> topoOrder;

    public DagPaths(int n, List<List<Edge>> adj, List<Integer> topoOrder) {
        this.n = n;
        this.adj = adj;
        this.topoOrder = topoOrder;
    }

    // Compute shortest paths from source using DP
    public int[] shortestPaths(int source) {
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[source] = 0;

        // Process nodes in topological order
        for (int u : topoOrder) {
            if (dist[u] != Integer.MAX_VALUE) {
                for (Edge e : adj.get(u)) {
                    int v = e.v;
                    int w = e.w;
                    if (dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                    }
                }
            }
        }

        return dist;
    }

    // Compute longest paths from source using max-DP
    public int[] longestPaths(int source) {
        int[] dist = new int[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MIN_VALUE;
        }
        dist[source] = 0;

        // Process nodes in topological order
        for (int u : topoOrder) {
            if (dist[u] != Integer.MIN_VALUE) {
                for (Edge e : adj.get(u)) {
                    int v = e.v;
                    int w = e.w;
                    if (dist[u] + w > dist[v]) {
                        dist[v] = dist[u] + w;
                    }
                }
            }
        }

        return dist;
    }

    // Find critical path (longest path overall)
    public PathResult findCriticalPath(int source) {
        int[] dist = new int[n];
        int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MIN_VALUE;
            parent[i] = -1;
        }
        dist[source] = 0;

        // Process in topological order with parent tracking
        for (int u : topoOrder) {
            if (dist[u] != Integer.MIN_VALUE) {
                for (Edge e : adj.get(u)) {
                    int v = e.v;
                    int w = e.w;
                    if (dist[u] + w > dist[v]) {
                        dist[v] = dist[u] + w;
                        parent[v] = u;
                    }
                }
            }
        }

        // Find node with maximum distance
        int maxNode = -1;
        int maxDist = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                maxNode = i;
            }
        }

        // Reconstruct path
        List<Integer> path = new ArrayList<>();
        if (maxNode != -1) {
            int curr = maxNode;
            while (curr != -1) {
                path.add(0, curr);
                curr = parent[curr];
            }
        }

        return new PathResult(path, maxDist);
    }

    // Reconstruct shortest path to target
    public List<Integer> reconstructShortestPath(int source, int target, int[] dist) {
        if (dist[target] == Integer.MAX_VALUE) {
            return null; // no path
        }

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }

        // Rebuild parent array
        for (int u : topoOrder) {
            if (dist[u] != Integer.MAX_VALUE) {
                for (Edge e : adj.get(u)) {
                    int v = e.v;
                    int w = e.w;
                    if (dist[u] + w == dist[v]) {
                        parent[v] = u;
                    }
                }
            }
        }

        // Reconstruct path
        List<Integer> path = new ArrayList<>();
        int curr = target;
        while (curr != -1) {
            path.add(0, curr);
            curr = parent[curr];
        }

        return path;
    }

    public static class PathResult {
        public List<Integer> path;
        public int length;

        public PathResult(List<Integer> path, int length) {
            this.path = path;
            this.length = length;
        }
    }
}