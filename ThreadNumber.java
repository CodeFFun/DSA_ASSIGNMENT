
public class ThreadNumber {
    public static void main(String[] args) throws InterruptedException {
        NumberPrinter printer = new NumberPrinter();
        ThreadController controller = new ThreadController(printer, 10);
        controller.startThreads();
    }
}

class NumberPrinter {
    public void printZero() {
        System.out.print("0");
    }

    public void printEven(int num) {
        System.out.print(num);
    }

    public void printOdd(int num) {
        System.out.print(num);
    }
}

class ThreadController {
    // Key synchronization variables
    private final NumberPrinter printer;  // Printer object to output numbers
    private final int n;                  // Maximum number to print
    private int currentNumber = 1;        // Current number to be printed
    private boolean zeroTurn = true;      // Flag to control zero printing
    private final Object lock = new Object();  // Synchronization lock object

    // Constructor to initialize controller
    public ThreadController(NumberPrinter printer, int n) {
        this.printer = printer;
        this.n = n;
    }

    public void startThreads() throws InterruptedException {
        // Zero Thread: Responsible for printing zeros
        Thread zeroThread = new Thread(() -> {
            try {
                while (true) {
                    synchronized (lock) {
                        // Wait until it's zero's turn
                        while (!zeroTurn) {
                            lock.wait();
                            if (currentNumber > n) break;
                        }
                        
                        // Exit condition
                        if (currentNumber > n) break;
                        
                        // Print zero
                        printer.printZero();
                        
                        // Signal that zero is printed, next turn is for either odd or even
                        zeroTurn = false;
                        lock.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Odd Thread: Responsible for printing odd numbers
        Thread oddThread = new Thread(() -> {
            try {
                while (true) {
                    synchronized (lock) {
                        // Wait conditions:
                        // 1. If it's zero's turn
                        // 2. If current number is even
                        // 3. If current number exceeds n
                        while (zeroTurn || currentNumber % 2 == 0 || currentNumber > n) {
                            lock.wait();
                            if (currentNumber > n) break;
                        }
                        
                        // Exit condition
                        if (currentNumber > n) break;
                        
                        // Print odd number
                        printer.printOdd(currentNumber);
                        
                        // Increment number and reset zero turn
                        currentNumber++;
                        zeroTurn = true;
                        lock.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Even Thread: Responsible for printing even numbers
        Thread evenThread = new Thread(() -> {
            try {
                while (true) {
                    synchronized (lock) {
                        // Wait conditions:
                        // 1. If it's zero's turn
                        // 2. If current number is odd
                        // 3. If current number exceeds n
                        while (zeroTurn || currentNumber % 2 != 0 || currentNumber > n) {
                            lock.wait();
                            if (currentNumber > n) break;
                        }
                        
                        // Exit condition
                        if (currentNumber > n) break;
                        
                        // Print even number
                        printer.printEven(currentNumber);
                        
                        // Increment number and reset zero turn
                        currentNumber++;
                        zeroTurn = true;
                        lock.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start all threads
        zeroThread.start();
        oddThread.start();
        evenThread.start();

        // Wait for all threads to complete
        zeroThread.join();
        oddThread.join();
        evenThread.join();
    }
}