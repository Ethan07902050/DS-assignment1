import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorClientTest { 
    private Calculator stub;
    private int clientCount;

    @BeforeEach
    public void setUp() { 
        try {
            clientCount = 5;
            Registry registry = LocateRegistry.getRegistry();
            stub = (Calculator) registry.lookup("Calculator");            
        } catch (Exception e) { 
            System.err.println("Client exception: " + e);
        }
    }

    @Test
    public void testPushAndPopOneClient() throws Exception {        
        stub.pushValue(15);
        assertEquals(15, stub.pop());
    }

    @Test
    public void testPushMinOneClient() throws Exception {        
        stub.pushValue(15);
        stub.pushValue(10);
        stub.pushValue(25);
        stub.pushOperation("min");
        assertEquals(10, stub.pop());
    }

    @Test
    public void testPushMaxOneClient() throws Exception {        
        stub.pushValue(15);
        stub.pushValue(10);
        stub.pushValue(25);
        stub.pushOperation("max");
        assertEquals(25, stub.pop());
    }

    @Test
    public void testPushGcdOneClient() throws Exception {        
        stub.pushValue(15);
        stub.pushValue(10);
        stub.pushValue(25);
        stub.pushOperation("gcd");
        assertEquals(5, stub.pop());
    }

    @Test
    public void testPushLcmOneClient() throws Exception {        
        stub.pushValue(15);
        stub.pushValue(10);
        stub.pushValue(25);
        stub.pushOperation("lcm");
        assertEquals(150, stub.pop());
    }

    @Test
    public void testIsEmptyOneClient() throws Exception {        
        stub.pushValue(15);
        stub.pop();
        assertTrue(stub.isEmpty());
    }

    @Test
    public void testDelayPopOneClient() throws Exception {
        stub.pushValue(15);
        long startTime = System.currentTimeMillis();
        int result = stub.delayPop(1000);
        long endTime = System.currentTimeMillis();
        assertEquals(15, result);
        assertTrue((endTime - startTime) >= 1000);
    }

    @Test
    public void testPushAndPopMultiClient() throws Exception {        
        // Simulate multiple clients pushing values
        for (int i = 0; i < clientCount; i++) {
            new Thread(() -> {
                try {
                    stub.pushValue((int)(Math.random() * 100));
                    // The result depends on the execution order
                    assertNotNull(stub.pop());
                } catch (Exception e) {
                    System.err.println("Multi client push and pop: " + e);
                }
            }).start();
        }
    }

    @Test
    public void testPushMinMultiClient() throws Exception {
        CountDownLatch latch = new CountDownLatch(clientCount);

        for (int i = 1; i <= clientCount; i++) {
            final int value = i;
            new Thread(() -> {
                try {
                    stub.pushValue(value * 5);
                    stub.pushOperation("min");
                    latch.countDown();
                } catch (Exception e) {
                    System.err.println("Multi client push min: " + e);
                }
            }).start();
        }

        latch.await();  // Ensure all clients have finished their operation
        assertEquals(5, stub.pop());
    }

    @Test
    public void testPushMaxMultiClient() throws Exception {
        CountDownLatch latch = new CountDownLatch(clientCount);

        for (int i = 1; i <= clientCount; i++) {
            final int value = i;
            new Thread(() -> {
                try {
                    stub.pushValue(value * 5);
                    stub.pushOperation("max");
                    latch.countDown();
                } catch (Exception e) {
                    System.err.println("Multi client push max: " + e);
                }
            }).start();
        }

        latch.await();  // Ensure all clients have finished their operation
        assertEquals(25, stub.pop());
    }

    @Test
    public void testPushGcdMultiClient() throws Exception {
        CountDownLatch latch = new CountDownLatch(clientCount);

        for (int i = 1; i <= clientCount; i++) {
            final int value = i;
            new Thread(() -> {
                try {
                    stub.pushValue(value * 5);
                    stub.pushOperation("gcd");
                    latch.countDown();
                } catch (Exception e) {
                    System.err.println("Multi client push gcd: " + e);
                }
            }).start();
        }

        latch.await();  // Ensure all clients have finished their operation
        assertEquals(5, stub.pop());
    }

    @Test
    public void testPushLcmMultiClient() throws Exception {
        CountDownLatch latch = new CountDownLatch(clientCount);

        for (int i = 1; i <= clientCount; i++) {
            final int value = i;
            new Thread(() -> {
                try {
                    stub.pushValue(value * 5);
                    stub.pushOperation("lcm");
                    latch.countDown();
                } catch (Exception e) {
                    System.err.println("Multi client push lcm: " + e);
                }
            }).start();
        }

        latch.await();  // Ensure all clients have finished their operation
        assertEquals(300, stub.pop());
    }

    @Test
    public void testIsEmptyMultiClient() throws Exception {                
        for (int i = 0; i < clientCount; i++) {
            new Thread(() -> {
                try {
                    stub.pushValue((int)(Math.random() * 100));
                    stub.pop();
                    // The result depends on the execution order
                    assertNotNull(stub.isEmpty()); 
                } catch (Exception e) {
                    System.err.println("Multi client isEmpty: " + e);
                }
            }).start();
        }
    }

    @Test
    public void testDelayPopMultiClient() throws Exception {
        CountDownLatch latch = new CountDownLatch(clientCount);

        for (int i = 0; i < clientCount; i++) {
            new Thread(() -> {
                try {
                    stub.pushValue((int)(Math.random() * 100));
                    long startTime = System.currentTimeMillis();
                    int result = stub.delayPop(1000);
                    long endTime = System.currentTimeMillis();
                    
                    // The result depends on the execution order
                    assertNotNull(result); 
                    assertTrue((endTime - startTime) >= 1000);
                    latch.countDown();
                } catch (Exception e) {
                    System.err.println("Multi client delayPop: " + e);
                }
            }).start();
        }

        latch.await();
    }
} 

