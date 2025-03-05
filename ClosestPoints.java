//2b
import java.util.Arrays;

//first we create a minDistane to highest possible value and create reult array to hold their positions and set it to -1
//we will then loop through arrays to compare every possible pair
//then we calculate the manhattan distance, if the manhattan distance is less than the minDistance then we will update the min distance
//if the distance is equal and first indices differ we choose the smaller first index. if first indices are same, choose the pair with smaller second index

public class ClosestPoints {
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length;
        int minDistance = Integer.MAX_VALUE; // Initialize to maximum possible value
        int[] result = new int[]{-1, -1};    // Array to store the result indices

        // Iterate over all possible pairs of points
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Calculate Manhattan distance
                int distance = Math.abs(x_coords[i] - x_coords[j]) + 
                               Math.abs(y_coords[i] - y_coords[j]);
                
                // Update if this distance is smaller
                if (distance < minDistance) {
                    minDistance = distance;
                    result[0] = i;
                    result[1] = j;
                } 
                // If distance is equal, choose lexicographically smaller pair
                else if (distance == minDistance) {
                    if (i < result[0] || (i == result[0] && j < result[1])) {
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
        }

        return result;
    }


    public static void main(String[] args) {
        // Example usage
        int[] x_coords = {1, 2, 3, 2, 4};
        int[] y_coords = {2, 3, 1, 2, 3};
        int[] result = findClosestPair(x_coords, y_coords);
        System.out.println("Input: " + Arrays.toString(x_coords) + Arrays.toString(x_coords) + "\n output: " + Arrays.toString(result));
    }
}
