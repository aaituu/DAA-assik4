package com.example.util;


public class Metrics {

    // SCC metrics
    public int dfsVisits = 0;
    public int dfsEdges = 0;

    // Topological sort metrics
    public int pushOps = 0;
    public int popOps = 0;

    // DAG shortest path metrics
    public int relaxations = 0;

    // Timing
    public long timeNanos = 0;

    public void reset() {
        dfsVisits = 0;
        dfsEdges = 0;
        pushOps = 0;
        popOps = 0;
        relaxations = 0;
        timeNanos = 0;
    }

    public double getTimeMillis() {
        return timeNanos / 1_000_000.0;
    }
}