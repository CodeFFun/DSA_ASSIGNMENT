/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networkgui;

/**
 *
 * @author user
 */
import java.util.*;

public class ShortestPath {
    
    // Function to find the shortest path using Dijkstra's Algorithm (bandwidth as weight)
    public static List<Connection> findShortestPath(List<Computer> computers, List<Connection> connections, Computer start, Computer end) {
        Map<Computer, Integer> distances = new HashMap<>();
        Map<Computer, Connection> previous = new HashMap<>();
        PriorityQueue<Computer> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // Initialize distances
        for (Computer c : computers) {
            distances.put(c, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            Computer current = pq.poll();

            // Stop if we reach the destination
            if (current == end) break;

            for (Connection conn : connections) {
                if (conn.from == current || conn.to == current) {
                    Computer neighbor = (conn.from == current) ? conn.to : conn.from;
                    int newDist = distances.get(current) + conn.bandwidth; // Use bandwidth as weight

                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                        previous.put(neighbor, conn);
                        pq.add(neighbor);
                    }
                }
            }
        }

        // Reconstruct the shortest path
        List<Connection> path = new ArrayList<>();
        Computer step = end;
        while (previous.containsKey(step)) {
            Connection conn = previous.get(step);
            path.add(conn);
            step = (conn.from == step) ? conn.to : conn.from; // Move to the previous node
        }
        Collections.reverse(path);
        return path;
    }
}

