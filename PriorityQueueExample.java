//1b


import java.util.Arrays;
import java.util.PriorityQueue;

//for this problem we will be using priority queue as min heap to help us sort the profuct in ascending order
//we return invalid output if the k is 0 or the value of k is greater than the total number of output r1 and r2 can produce
// we will then loop through r1 and r2 multiplying each element and adding it to the priority queue
//then we will loop through rpiorityqueue until we reach k and return the value


public class PriorityQueueExample {
    static int findTerm(int[] r1, int[] r2, int k){
        if(k == 0 || k > r1.length * r2.length){ //check if k is grater than element created by multiplting two elements or is zero
            return -1;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(); //initialize priorityqueue

        for(int i = 0; i < r1.length ; i++){ //multiply every element with each other
            for(int j = 0; j < r2.length; j++){
                queue.add(r1[i] * r2[j]);
            }
        }

        int result = 0; //initialzie the result

        for(int i = 0; i < k; i++){ //loop until k
            result = queue.poll();
        }
        return result; //returns the kth element
    }

    public static void main(String[] args) {
        int[] r1= new int[] {-4,-2,0,3};
        int[] r2= new int[] {2,4};
        int[] r3 = new int[] {2,5};
        int[] r4 = new int[] {3,4};

        System.out.println("Input: " + Arrays.toString(r1) + " " + Arrays.toString(r2) + "\n output: " + findTerm(r1, r2,6));
        System.out.println("Input: " + Arrays.toString(r3) + " " + Arrays.toString(r4) + "\n output: " + findTerm(r3, r4,2));

        /* Input: [-4, -2, 0, 3] [2, 4]
            output: 0
            Input: [2, 5] [3, 4]
            output: 8 
        */

    }
}
