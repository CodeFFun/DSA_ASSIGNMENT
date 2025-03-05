/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networkgui;

/**
 *
 * @author user
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GraphOptimizer {

    // Finds the Minimum Spanning Tree using Kruskal’s Algorithm
    public static List<Connection> findOptimalNetwork(List<Computer> computers, List<Connection> connections) {
        List<Connection> mst = new ArrayList<>();
        Collections.sort(connections, Comparator.comparingInt(c -> c.cost)); // Sort connections by cost

        DisjointSet ds = new DisjointSet(computers.size());
        for (int i = 0; i < computers.size(); i++) {
            ds.makeSet(i);
        }

        for (Connection conn : connections) {
            int fromIndex = computers.indexOf(conn.from);
            int toIndex = computers.indexOf(conn.to);
            
            if (ds.find(fromIndex) != ds.find(toIndex)) { // If they are not already connected
                mst.add(conn);
                ds.union(fromIndex, toIndex);
            }
        }
        return mst; // Return optimized network
    }
}

// Disjoint Set (Union-Find) for Kruskal's Algorithm
class DisjointSet {
    private int[] parent, rank;

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];
    }

    public void makeSet(int v) {
        parent[v] = v;
        rank[v] = 0;
    }

    public int find(int v) {
        if (parent[v] != v) {
            parent[v] = find(parent[v]); // Path compression
        }
        return parent[v];
    }

    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA != rootB) {
            if (rank[rootA] > rank[rootB]) {
                parent[rootB] = rootA;
            } else {
                parent[rootA] = rootB;
                if (rank[rootA] == rank[rootB]) {
                    rank[rootB]++;
                }
            }
        }
    }
}
