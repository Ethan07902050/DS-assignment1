# Distributed Systems Assignment 1

## Execution
1. Download JUnit Console Launcher.
```
wget https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.3/junit-platform-console-standalone-1.10.3.jar
```

1. Compile all the java files.
```
javac -d out -cp junit-platform-console-standalone-1.10.3.jar *.java
```

2. Start with the registry. 
```
cd out
rmiregistry
```

3. Run the server in the second console.
```
java -cp out CalculatorServer
```

4. Test the client with JUnit in the last console.
```
java -jar junit-platform-console-standalone-1.10.3.jar --class-path out --scan-class-path
```

## Tests Overview

### 1. **Single Client Tests**
These tests validate the basic functionality of the calculator service when accessed by a single client. Each test method simulates a sequence of operations performed by one client and verifies that the server returns the expected results.

- **testPushAndPopOneClient**: Tests the ability to push a single value onto the stack and then pop it off, verifying the value returned is correct.
- **testPushMinOneClient**: Tests the "min" operation by pushing multiple values onto the stack and then verifying that the smallest value is correctly identified and returned.
- **testPushMaxOneClient**: Tests the "max" operation by pushing multiple values onto the stack and then verifying that the largest value is correctly identified and returned.
- **testPushGcdOneClient**: Tests the "gcd" operation by pushing multiple values onto the stack and verifying that the greatest common divisor of the values is correctly calculated and returned.
- **testPushLcmOneClient**: Tests the "lcm" operation by pushing multiple values onto the stack and verifying that the least common multiple of the values is correctly calculated and returned.
- **testIsEmptyOneClient**: Tests the `isEmpty` method by verifying that after pushing and popping a value, the stack is indeed empty.
- **testDelayPopOneClient**: Tests the `delayPop` method by pushing a value and verifying that the pop operation is delayed as expected.

### 2. **Multi-Client Tests**
These tests simulate concurrent access to the calculator service by multiple clients. They are designed to test the server's ability to handle simultaneous operations from multiple clients, ensuring thread safety and consistent results.

- **testPushAndPopMultiClient**: Simulates multiple clients pushing values onto the stack and popping them off concurrently. The test verifies that the popped values are not `null`, ensuring that each client gets a valid response.
- **testPushMinMultiClient**: Simulates multiple clients pushing values and performing the "min" operation concurrently. The test verifies that the smallest value across all clients is correctly calculated and returned.
- **testPushMaxMultiClient**: Simulates multiple clients pushing values and performing the "max" operation concurrently. The test verifies that the largest value across all clients is correctly calculated and returned.
- **testPushGcdMultiClient**: Simulates multiple clients pushing values and performing the "gcd" operation concurrently. The test verifies that the greatest common divisor of the values pushed by all clients is correctly calculated and returned.
- **testPushLcmMultiClient**: Simulates multiple clients pushing values and performing the "lcm" operation concurrently. The test verifies that the least common multiple of the values pushed by all clients is correctly calculated and returned.
- **testIsEmptyMultiClient**: Simulates multiple clients pushing and popping values concurrently and verifies that the stack's empty state is correctly reported after operations.
- **testDelayPopMultiClient**: Simulates multiple clients performing delayed pop operations concurrently and verifies that the delays are correctly respected, and valid values are returned.
