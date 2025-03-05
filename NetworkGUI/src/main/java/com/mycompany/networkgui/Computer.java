/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.networkgui;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Computer {
    public int x, y;
    public String type = "";
    public List<Connection> connections = new ArrayList<>();     
    
    
    public Computer(int x, int y, String type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
}

