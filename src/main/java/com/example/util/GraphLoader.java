package com.example.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GraphLoader {
    public static class Edge{
        public int u;
        public int v;
        public int w;

        public Edge(int u,int v,int w){
            this.u=u;
            this.v=v;
            this.w=w;
        }
    }

    public static class GraphData {
        public int n;
        public List<Edge> edges;
        public int source;
        public String weightModel;

        public GraphData(){
            edges=new ArrayList<>();
        }
    }

    public static GraphData loadGraph (String filePath) throws IOException{
        String content = Files.readString(Paths.get(filePath));
        JSONObject json = new JSONObject(content);

        GraphData data = new GraphData();
        data.n=json.getInt("n");

        JSONArray edgesArray = json.getJSONArray("edges");
        for(int i=0;i<edgesArray.length();i++){
            JSONObject edge = edgesArray.getJSONObject(i);
            int u = edge.getInt("u");
            int v = edge.getInt("v");
            int w = edge.getInt("w");
            data.edges.add(new Edge(u,v,w));
        }

        if (json.has("source")){
            data.source = json.getInt("source");
        }

        if (json.has("weight_model")){
            data.weightModel=json.getString("weight_model");
        }
        else {
            data.weightModel="edge";
        }
        return data;
    }

    public static List<List<Integer>> buildAdjList (int n, List<Edge> edges){
        List<List<Integer>> adjList=new ArrayList<>();
        for(int i=0;i<n;i++){
            adjList.add(new ArrayList<>());
        }
        for(Edge e:edges){
            adjList.get(e.u).add(e.v);
        }
        return adjList;
    }

    public static List<List<Edge>> buildWeightedAdjList(int n, List<Edge> edges){
        List<List<Edge>> adjList=new ArrayList<>();
        for(int i=0;i<n;i++){
            adjList.add(new ArrayList<>());
        }

        for(Edge e:edges){
            adjList.get(e.u).add(e);
        }

        return adjList;
    }



}
