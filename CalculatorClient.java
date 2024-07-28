import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient { 
    private static Calculator stub;

    public static void main(String[] args) { 
        try {
            Registry registry = LocateRegistry.getRegistry();
            stub = (Calculator) registry.lookup("Calculator");

            System.out.println("One client");
            testOneClient();

            System.out.println("Multiple clients");
            testMultiClient();            
        } catch (Exception e) { 
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
         
    }

    private static void testOneClient() {
        try {
            stub.pushValue(15);
            stub.pushValue(10);
            stub.pushValue(25);
            stub.pushOperation("min");
            int minValue = stub.pop();
            System.out.println("minValue: " + minValue);

            stub.pushValue(15);
            stub.pushValue(10);
            stub.pushValue(25);
            stub.pushOperation("max");
            int maxValue = stub.pop();
            System.out.println("maxValue: " + maxValue);

            stub.pushValue(15);
            stub.pushValue(10);
            stub.pushValue(25);
            stub.pushOperation("lcm");
            int lcmValue = stub.pop();
            System.out.println("lcmValue: " + lcmValue);

            stub.pushValue(15);
            stub.pushValue(10);
            stub.pushValue(25);
            stub.pushOperation("gcd");
            int gcdValue = stub.delayPop(3000);
            System.out.println("gcdValue: " + gcdValue);

            boolean isEmpty = stub.isEmpty();
            System.out.println("isEmpty: " + isEmpty);
        } catch (Exception e) { 
            System.err.println("One client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void testMultiClient() {
        try {
            // Create and start multiple clients with different operations
            Thread client1 = new Thread(CalculatorClient::Client1);
            Thread client2 = new Thread(CalculatorClient::Client2);
            Thread client3 = new Thread(CalculatorClient::Client3);
            Thread client4 = new Thread(CalculatorClient::Client4);
            Thread client5 = new Thread(CalculatorClient::Client5);

            client1.start();
            client2.start();
            client3.start();
            client4.start();
            client5.start();

            client1.join();
            client2.join();
            client3.join();
            client4.join();
            client5.join();
        } catch (Exception e) { 
            System.err.println("Multi client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void Client1() {
        try {
            // Test if pop works
            stub.pushValue(5);
            stub.pop();

            stub.pushValue(15);
            stub.pushValue(10);
            stub.pushValue(25);
            stub.pushOperation("min");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Client2() {
        try {
            // Wait for 1 second and pop
            int minValue = stub.delayPop(1000);
            System.out.println("minValue: " + minValue);
            
            stub.pushValue(15);
            stub.pushValue(10);
            stub.pushValue(25);
            stub.pushOperation("max");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Client3() {
        try {
            // Wait for 2 seconds and pop
            int maxValue = stub.delayPop(2000);
            System.out.println("maxValue: " + maxValue);
            
            stub.pushValue(15);
            stub.pushValue(10);
            stub.pushValue(25);
            stub.pushOperation("lcm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Client4() {
        try {
            // Wait for 3 seconds and pop
            int lcmValue = stub.delayPop(3000);
            System.out.println("lcmValue: " + lcmValue);
            
            stub.pushValue(15);
            stub.pushValue(10);
            stub.pushValue(25);
            stub.pushOperation("gcd");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void Client5() {
        try {
            // Wait for 4 seconds and pop
            int gcdValue = stub.delayPop(4000);
            System.out.println("gcdValue: " + gcdValue);

            boolean isEmpty = stub.isEmpty();
            System.out.println("isEmpty: " + isEmpty);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 

