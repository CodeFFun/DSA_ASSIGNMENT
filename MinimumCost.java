//3a
import java.util.*;

//first we create an array to store all possible edges
//then we will add all the exisisting connections
//we will then add virtual edges from virtual end to existing device
//we will then sort all edges in ascending order and  to track connected device
//we will then us kruskals  algrirythm to find minimum spanning tree
//we will find the root of each device and if they are in different components then we will connect them

public class MinimumCost {
    public static int minCost(int n, int[] modules, int[][] connections) {
        List<int[]> edges = new ArrayList<>();
        
        // Add all connections, converting to 0-based indices
        for (int[] conn : connections) {
            int u = conn[0] - 1; // Convert to 0-based
            int v = conn[1] - 1;
            int cost = conn[2];
            edges.add(new int[]{u, v, cost});
        }
        
        // Add edges from the virtual node  to each device
        int virtualNode = n;
        for (int i = 0; i < n; i++) {
            edges.add(new int[]{i, virtualNode, modules[i]});
        }
        
        // Sort edges by cost
        Collections.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));
        
        // Union-Find (Disjoint Set Union) initialization
        int[] parent = new int[n + 1]; // Nodes are 0 to n
        for (int i = 0; i <= n; i++) {
            parent[i] = i; // Each node is initially its own parent
        }
        
        int totalCost = 0; // Total cost of connections
        int edgesUsed = 0; // Track number of edges in minimum spanning tree
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];

           // Find root/representative of each device's componen
            
            int rootU = find(parent, u);
            int rootV = find(parent, v);

            //if devices are in different components, connect them
            
            if (rootU != rootV) {
                parent[rootU] = rootV;
                totalCost += cost;
                edgesUsed++;
                 // Stop when all devices are connected
                if (edgesUsed == n) { // MST for n+1 nodes has n edges
                    break;
                }
            }
        }
        
        return totalCost;
    }
    
    private static int find(int[] parent, int x) {
        // Update parent to root during traversal
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }
    
    public static void main(String[] args) {
        int n = 3;
        int[] modules = {1, 2, 2};
        int[][] connections = {{1, 2, 1}, {2, 3, 1}};
         String connectionsStr = Arrays.deepToString(connections);
        
         int result = minCost(n, modules, connections);
         
         System.out.println("Input: n = " + n + 
                            ", modules = " + Arrays.toString(modules) + 
                            ", connections = " + connectionsStr + 
                            "\nOutput: " + result);
     }
}