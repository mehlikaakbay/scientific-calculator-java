package calculator;

import java.util.Arrays;

public class CalculatorEngine {
    private double memory = 0;                           // Memory storage
    private final StringBuilder history = new StringBuilder(); // StringBuilder for efficient string building
    
    // Factory method pattern - creates different operation types
    public Operation createOperation(String operationType, String... params) {
        // Switch expression with yield
        return switch (operationType.toLowerCase()) {
            case "arithmetic" -> {
                if (params.length < 1) {
                    throw new IllegalArgumentException("Arithmetic operation requires operator parameter");
                }
                yield new ArithmeticOperation(params[0]);  // Object instantiation
            }
            
            case "trigonometric" -> {
                if (params.length < 1) {
                    throw new IllegalArgumentException("Trigonometric operation requires function parameter");
                }
                
                // Boolean.parseBoolean() for string to boolean conversion
                boolean isInverse = params.length > 1 && Boolean.parseBoolean(params[1]);
                yield new TrigonometricOperation(params[0], isInverse);
            }
            
            case "logarithmic" -> {
                if (params.length < 1) {
                    throw new IllegalArgumentException("Logarithmic operation requires function parameter");
                }
                yield new LogarithmicOperation(params[0]);
            }
            
            case "exponential" -> {
                if (params.length < 1) {
                    throw new IllegalArgumentException("Exponential operation requires function parameter");
                }
                yield new ExponentialOperation(params[0]);
            }
            
            case "special" -> {
                if (params.length < 1) {
                    throw new IllegalArgumentException("Special operation requires function parameter");
                }
                yield new SpecialOperation(params[0]);
            }
            
            default -> throw new IllegalArgumentException("Unknown operation type: " + operationType);
        };
    }
    
    // Execute operation and log result
    public String executeOperation(Operation operation, double... operands) throws ArithmeticException {
        
        String result = operation.performOperation(operands);  // Polymorphic method call
        
        // Arrays.toString() for array to string conversion
        String operandsStr = Arrays.toString(operands);
        String logEntry = operation.getOperationName() + ": " + operandsStr + " = " + result;
        appendToHistory(logEntry);
        
        return result;
    }
    
    // Memory operations
    public void storeInMemory(double value) {
        this.memory = value;
        appendToHistory("Memory Store: " + value);
    }
    
    public double recallFromMemory() {
        appendToHistory("Memory Recall: " + memory);
        return memory;
    }
    
    public void addToMemory(double value) {
        this.memory += value;  // Compound assignment operator
        appendToHistory("Memory Add: +" + value + " = " + memory);
    }
    
    public void subtractFromMemory(double value) {
        this.memory -= value;  // Compound assignment operator
        appendToHistory("Memory Subtract: -" + value + " = " + memory);
    }
    
    public void clearMemory() {
        this.memory = 0;
        appendToHistory("Memory Cleared");
    }
    
    public double getMemoryValue() {
        return memory;
    }
    
    // StringBuilder.append() for efficient string concatenation
    public void appendToHistory(String log) {
        history.append(log).append("\n");
    }
    
    // StringBuilder.toString() conversion
    public String getHistory() {
        return history.toString();
    }
    
    // StringBuilder.length() method
    public boolean isHistoryEmpty() {
        return history.length() == 0;
    }
    
    // StringBuilder.setLength() to clear content
    public void clearHistory() {
        history.setLength(0);
        appendToHistory("History Cleared");
    }
    
    // Input validation method
    public boolean isValidNumber(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        try {
            Double.parseDouble(text.trim());  // Try-catch for validation
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // Parse string to double with error handling
    public double parseNumber(String text) throws NumberFormatException {
        if (text == null || text.trim().isEmpty()) {
            throw new NumberFormatException("Empty input");
        }
        
        return Double.parseDouble(text.trim());
    }
    
    // String.format() for formatted output
    public String getEngineStatus() {
        return String.format("Engine Status - Memory: %.2f, History entries: %d", 
                           memory, history.toString().split("\n").length);
    }
}