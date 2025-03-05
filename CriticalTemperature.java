//1a

// for this problem we will use dynamic programmingf since it will be easier to store the data(memoization) and makes the code more optimized
//To do this first we will create the 2d dp array that stores the temperature and number of samples left
//after that we will then give INF when sample is zero since if there is no sample we cant conduct any experiment
//we will initialize zero if the temperature left to check is zero
//then we will use binary search inorder tro determine the optimal temperature
//we will then fill the dp table and return the last most answer in an array to show minimum attempts required

public class CriticalTemperature {

    public static int findMinMeasurements(int k, int n) {
        //  large value to avoid overflow (n+1 is safe)
        int INF = n + 1;
        int[][] dp = new int[k + 1][n + 1]; //initialize array

        // Base cases:
        // 0 temperature levels: 0 measurements needed
        for (int i = 1; i <= k; i++) {
            dp[i][0] = 0;
        }
        // 0 samples: impossible (use INF)
        for (int j = 1; j <= n; j++) {
            dp[0][j] = INF;
        }

        // Fill the DP table
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = INF; // Initialize to "infinity"
                int left = 1, right = j;
                // Binary search to find the optimal temperature to test
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    int broken = dp[i - 1][mid - 1]; // If material rect then we go down mid
        
                    int notBroken = dp[i][j - mid];// If material doesn't rect then we go up mid
                    // The worst-case scenario 
                    int worstCase = 1 + Math.max(broken, notBroken); // adding 1 beacsue we just used our 1 attemp
                    // Update the DP table if this scenario is better
                    if (worstCase < dp[i][j]) {
                        dp[i][j] = worstCase;
                    }
                    // Adjust binary search bounds based on comparison
                    if (broken > notBroken) {
                        right = mid - 1;    
                    } else {
                        left = mid + 1;
                    }
                }
            }
        }

        return dp[k][n];//return the end of the dp array
    }

    public static void main(String[] args) {
        int k = 2;
        int n = 6;
        int k1 = 3;
        int n1 = 14;
        int result = findMinMeasurements(k, n);
        int result1 = findMinMeasurements(k1, n1);
        System.out.println("Minimum number of measurements required for sample " + k + " and temperature " + n + " is " + result);
        System.out.println("Minimum number of measurements required for sample " + k1 + " and temperature " + n1 + " is " + result1);

        //Minimum number of measurements required for sanmple 2 and temperature 6 is 3
        //Minimum number of measurements required for sanmple 2 and temperature 14 is 4
    }
}