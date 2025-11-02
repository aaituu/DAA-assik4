package com.example.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {

    public static class ResultRow {
        public String filename;
        public int vertices;
        public int edges;
        public int sccs;
        public int topoSize;
        public int dfsVisits;
        public int dfsEdges;
        public int pushOps;
        public int popOps;
        public int relaxations;
        public double timeMs;

        public ResultRow(String filename, int vertices, int edges, int sccs, int topoSize,
                         int dfsVisits, int dfsEdges, int pushOps, int popOps,
                         int relaxations, double timeMs) {
            this.filename = filename;
            this.vertices = vertices;
            this.edges = edges;
            this.sccs = sccs;
            this.topoSize = topoSize;
            this.dfsVisits = dfsVisits;
            this.dfsEdges = dfsEdges;
            this.pushOps = pushOps;
            this.popOps = popOps;
            this.relaxations = relaxations;
            this.timeMs = timeMs;
        }
    }

    public static void exportToCSV(List<ResultRow> results, String outputPath) throws IOException {
        FileWriter writer = new FileWriter(outputPath);

        // Write header
        writer.write("File,Vertices,Edges,SCCs,TopoSize,DFSVisits,DFSEdges,PushOps,PopOps,Relaxations,TimeMs\n");

        // Write data rows
        for (ResultRow row : results) {
            writer.write(String.format("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%.3f\n",
                    row.filename,
                    row.vertices,
                    row.edges,
                    row.sccs,
                    row.topoSize,
                    row.dfsVisits,
                    row.dfsEdges,
                    row.pushOps,
                    row.popOps,
                    row.relaxations,
                    row.timeMs
            ));
        }

        writer.close();
    }
}