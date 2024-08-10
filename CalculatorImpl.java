import java.rmi.RemoteException;
import java.util.Stack;

public class CalculatorImpl implements Calculator { 
 
    private Stack<Integer> stack;

    // Implementations must have an explicit constructor 
    // in order to declare the RemoteException exception 
    public CalculatorImpl() throws RemoteException { 
        stack = new Stack<>();
    } 

    public synchronized void pushValue(int val) 
        throws RemoteException {
        stack.push(val);
    }
 
    public synchronized void pushOperation(String operator) 
        throws RemoteException {
        if (operator.equals("min")) pushMin();
        else if (operator.equals("max")) pushMax();
        else if (operator.equals("lcm")) pushLcm();
        else if (operator.equals("gcd")) pushGcd();
    }

    public synchronized int pop() throws RemoteException {
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return stack.pop();
    }

    public synchronized boolean isEmpty() throws RemoteException {
        return stack.isEmpty();
    }

    public synchronized int delayPop(int millis) throws RemoteException, InterruptedException {
        Thread.sleep(millis); // Delay for the specified time
        if (stack.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return stack.pop(); // Pop the element from the stack
    }

    // Utility method to calculate GCD of two numbers
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Utility method to calculate LCM of two numbers
    private static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    // Method to push the minimum value of all popped values
    private synchronized void pushMin() {
        if (stack.isEmpty()) {
            return;
        }
        int minValue = stack.pop();
        while (!stack.isEmpty()) {
            minValue = Math.min(minValue, stack.pop());
        }
        stack.push(minValue);
    }

    // Method to push the maximum value of all popped values
    private synchronized void pushMax() {
        if (stack.isEmpty()) {
            return;
        }
        int maxValue = stack.pop();
        while (!stack.isEmpty()) {
            maxValue = Math.max(maxValue, stack.pop());
        }
        stack.push(maxValue);
    }

    // Method to push the LCM of all popped values
    private synchronized void pushLcm() {
        if (stack.isEmpty()) {
            return;
        }
        int lcmValue = stack.pop();
        while (!stack.isEmpty()) {
            
            int stackTop = stack.pop();
            lcmValue = lcm(lcmValue, stackTop);
        }
        stack.push(lcmValue);
    }

    // Method to push the GCD of all popped values
    public synchronized void pushGcd() {
        if (stack.isEmpty()) {
            return;
        }
        int gcdValue = stack.pop();
        while (!stack.isEmpty()) {
            gcdValue = gcd(gcdValue, stack.pop());
        }
        stack.push(gcdValue);
    }
} 

