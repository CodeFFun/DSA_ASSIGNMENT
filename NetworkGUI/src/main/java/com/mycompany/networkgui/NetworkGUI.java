/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.networkgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NetworkGUI {
    private static NetworkPanel panel;
    private static List<Computer> computers = new ArrayList<>();
    private static List<Connection> connections = new ArrayList<>();
    private static JLabel totalCostLabel;
       private static JLabel totalLatencyLabel;

    public static void main(String[] args) {
        JFrame window = new JFrame("Network GUI");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(1280, 720);
        window.setLocationRelativeTo(null);
        
        JPanel analysisPanel = new JPanel();
        totalCostLabel = new JLabel("Total Cost: 0");
        totalLatencyLabel = new JLabel("Total Latency: 0");

        analysisPanel.add(totalCostLabel);
        analysisPanel.add(totalLatencyLabel);
        window.add(analysisPanel, BorderLayout.NORTH);

        updateAnalysis();

        // Create computers (server + clients)
        Computer server = new Computer(1280/2, 720/2, "server");
        Computer client1 = new Computer(1280/2 - 400, 720/2, "client");
        Computer client2 = new Computer(1280/2, 720/2 - 200, "client");
        Computer client3 = new Computer(1280/2 + 400, 720/2, "client");

        computers.add(server);
        computers.add(client1);
        computers.add(client2);
        computers.add(client3);

        // Create connections (each connection has cost & bandwidth)
        connections.add(new Connection(server, client1, 10, 100));
        connections.add(new Connection(server, client2, 15, 200));
        connections.add(new Connection(server, client3, 20, 150));
        connections.add(new Connection(client1, client2, 5, 50));
        connections.add(new Connection(client2, client3, 8, 75));

        panel = new NetworkPanel(computers, connections);
        window.add(panel, BorderLayout.CENTER);

        // ✅ Dropdowns to select start and end nodes
        JComboBox<String> startNodeDropdown = new JComboBox<>();
        JComboBox<String> endNodeDropdown = new JComboBox<>();
        for (Computer c : computers) {
            startNodeDropdown.addItem(c.type + " (" + c.x + ", " + c.y + ")");
            endNodeDropdown.addItem(c.type + " (" + c.x + ", " + c.y + ")");
        }

        //Button to find the shortest path
        JButton shortestPathButton = new JButton("Find Shortest Path");
        shortestPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int startIndex = startNodeDropdown.getSelectedIndex();
                int endIndex = endNodeDropdown.getSelectedIndex();
                if (startIndex == endIndex) {
                    JOptionPane.showMessageDialog(window, "Start and End nodes must be different.");
                    return;
                }

                //  Calculate the shortest path
                Computer start = computers.get(startIndex);
                Computer end = computers.get(endIndex);
                List<Connection> shortestPath = ShortestPath.findShortestPath(computers, connections, start, end);

                // Highlight the shortest path
                panel.setHighlightedConnections(shortestPath);
                panel.repaint();
                updateAnalysis();
            }
        });

        // Button to optimize the network (Kruskal's Algorithm)
        JButton optimizeButton = new JButton("Optimize Network");
        optimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Connection> optimizedConnections = NetworkOptimizer.kruskalMST(computers, connections);

                // Update the panel to show only optimized connections
                panel.setConnections(optimizedConnections);
                panel.repaint();
                updateAnalysis();
            }
        });

      
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Start: "));
        controlPanel.add(startNodeDropdown);
        controlPanel.add(new JLabel("End: "));
        controlPanel.add(endNodeDropdown);
        controlPanel.add(shortestPathButton);
        controlPanel.add(optimizeButton);
        window.add(controlPanel, BorderLayout.SOUTH);

        window.setVisible(true);
    }
    
    private static void updateAnalysis() {
    int totalCost = 0;
    double totalLatency = 0.0; // Assuming latency = 1 / bandwidth

    for (Connection conn : connections) {
        totalCost += conn.cost;
        totalLatency += 1.0 / conn.bandwidth; // Lower bandwidth = Higher latency
    }

    totalCostLabel.setText("Total Cost: " + totalCost);
    totalLatencyLabel.setText("Total Latency: " + String.format("%.3f", totalLatency));
}

}



