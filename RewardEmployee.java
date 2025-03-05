//2a

//first we start by assigning each employee a rating of 1
//We will then move through the ratings to identify where it is increasing or decreasing
//if it is increasing then we increment the reward by one more than the previous
//if it is decreasing then we will decrease the reward 

import java.util.Arrays;

public class RewardEmployee {
    public static int Reward(int[] ratings) {
        int n = ratings.length;
        int i = 1;
        int total = n; //total defines the reward each employee gets hwich is 1
        while (i < n) {
            //we skip equal adjacent rating
            if (ratings[i] == ratings[i - 1]) {
                i++;
                continue;
            }
            
            int currentPeak = 0;
            //looping through increasing sequence until peak
            while (i < n && ratings[i] > ratings[i - 1]) {
                currentPeak++;
                total += currentPeak; // add reward for every increasing value
                i++;
            }

            //if array length is equal to peak then we return the answer
            if (i == n) {
                return total;
            }

            //now we loop through decreasing sequence from peak
            int currentValley = 0;
            while (i < n && ratings[i] < ratings[i - 1]) {
                currentValley++;
                total += currentValley;
                i++;
            }

            //adjust by subtracting the minimum of two to remove peak value redundency
            total -= Math.min(currentPeak, currentValley);
        }

        return total;        
    }

    public static void main(String[] args) {
        
        int[] ratings1 = {1, 0, 2};
        System.out.println("Input: " + Arrays.toString(ratings1) + "\n output: " + Reward(ratings1)); 

        
        int[] ratings2 = {1, 2, 2};
        System.out.println("Input: " + Arrays.toString(ratings2) + "\n output: " + Reward(ratings2)); 

        /*
         * Input: [1, 0, 2]
            output: 5
            Input: [1, 2, 2]
            output: 4
         */
        
    }
}
