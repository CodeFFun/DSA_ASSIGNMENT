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

public class NetworkOptimizer {
    
    public static List<Connection> kruskalMST(List<Computer> computers, List<Connection> connections) {
        List<Connection> result = new ArrayList<>();
        Map<Computer, Computer> parent = new HashMap<>();
        
        for (Computer c : computers) parent.put(c, c); // Initialize each node as its own parent

        // Sort connections by cost (ascending)
        connections.sort(Comparator.comparingInt(c -> c.cost));

        for (Connection conn : connections) {
            Computer root1 = find(parent, conn.from);
            Computer root2 = find(parent, conn.to);

            if (root1 != root2) {
                result.add(conn);
                parent.put(root1, root2); // Union operation
            }
        }
        return result;
    }

    private static Computer find(Map<Computer, Computer> parent, Computer c) {
        if (parent.get(c) != c) {
            parent.put(c, find(parent, parent.get(c)));
        }
        return parent.get(c);
    }
}

