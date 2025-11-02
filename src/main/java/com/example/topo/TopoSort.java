package com.example.topo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopoSort {
    private int n;
    private List<List<Integer>> adj;

    public TopoSort(int n,  List<List<Integer>> adj) {
        this.n = n;
        this.adj = adj;
    }

    public List<Integer> khanSort(){
        int [] inDegree = new int[n];

        for (int u =0;u<n;u++){
            for (int v:adj.get(u)){
                inDegree[v]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i =0;i<n;i++){
            if (inDegree[i]==0){
                q.add(i);
            }
        }
        List<Integer> topoOrder = new LinkedList<>();

        while (!q.isEmpty()){
            int u = q.poll();
            topoOrder.add(u);


            for(int v:adj.get(u)){
                inDegree[v]--;
                if (inDegree[v]==0){
                    q.add(v);
                }
            }
        }

        if (topoOrder.size()!=n){
            return null;
        }
        return topoOrder;
    }
}
