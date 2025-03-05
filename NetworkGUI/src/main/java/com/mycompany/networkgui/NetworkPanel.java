/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networkgui;

/**
 *
 * @author user
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NetworkPanel extends JPanel {
    private List<Computer> computers;
    private List<Connection> connections;
    private List<Connection> highlightedConnections; 

    public NetworkPanel(List<Computer> computers, List<Connection> connections) {
        this.computers = computers;
        this.connections = connections;
        this.highlightedConnections = null;
        setPreferredSize(new Dimension(1280, 720));
    }

   
    public void setConnections(List<Connection> connections) {
        this.connections = connections;
        this.highlightedConnections = null;
    }

    
    public void setHighlightedConnections(List<Connection> highlightedConnections) {
        this.highlightedConnections = highlightedConnections;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        g.setColor(Color.BLUE);
        for (Connection conn : connections) {
            g.drawLine(conn.from.x, conn.from.y, conn.to.x, conn.to.y);
            
            
            int midX = (conn.from.x + conn.to.x) / 2;
            int midY = (conn.from.y + conn.to.y) / 2;
            g.setColor(Color.BLACK);
            g.drawString(": " + conn.cost + "\n BW: " + conn.bandwidth, midX, midY - 5);
        }

        
        if (highlightedConnections != null) {
            g.setColor(Color.RED);
            for (Connection conn : highlightedConnections) {
                g.drawLine(conn.from.x, conn.from.y, conn.to.x, conn.to.y);
            }
        }

        
        for (Computer comp : computers) {
            g.setColor(comp.type.equals("server") ? Color.RED : Color.GREEN);
            g.fillOval(comp.x - 10, comp.y - 10, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString(comp.type, comp.x - 15, comp.y - 15);
        }
    }
}



