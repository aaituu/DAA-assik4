package com.example.scc;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SccFinder {
    private int n;
    private List<List<Integer>> adj;
    private int[] low;
    private int[] disc;
    private boolean[] onStack;
    private Stack<Integer> stack;
    private int time;
    private List<List<Integer>> sccs;

     public SccFinder(int n, List<List<Integer>> adj){
         this.n = n;
         this.adj = adj;
         low = new int[n];
         disc = new int[n];
         onStack = new boolean[n];
         stack = new Stack<>();
         time = 0;
         sccs = new ArrayList<>();

     }

    public List<List<Integer>> findSCCs() {
        for (int i = 0; i < n; i++){
            disc[i] = -1;
        }
        for  (int i = 0; i < n; i++){
            if(disc[i] == -1){
                tarjanDFS(i);
            }
        }
        return sccs;
    }


    private void tarjanDFS(int u){
         disc[u] = time;
         low[u] = time;
         time++;
         stack.push(u);
         onStack[u] = true;

         for (int v : adj.get(u)){
             if (disc[v] == -1){
                 tarjanDFS(v);
                 low[u] = Math.min(low[u], low[v]);
             }
             else if (onStack[v]){
                 low[u] = Math.min(low[u], disc[v]);
             }
         }
         if (low[u] == disc[u]){
             List<Integer> scc = new ArrayList<>();
             while (true){
                 int v = stack.pop();
                 onStack[v] = false;
                 scc.add(v);
                 if (v==u) {
                     break;
                 }
             }
             sccs.add(scc);
         }

    }



}
