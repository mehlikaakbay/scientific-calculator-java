package calculator;

// Abstract class definition
public abstract class Operation {
    protected String operationName;  // Protected field for inheritance
    
    // Constructor
    public Operation(String operationName) {
        this.operationName = operationName;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract double calculate(double... operands) throws ArithmeticException;
    
    // Template method pattern - calls abstract calculate()
    public String performOperation(double... operands) throws ArithmeticException {
        double result = calculate(operands);
        return formatResult(result);  // Method call
    }
    
    // Protected method for subclass access
    protected String formatResult(double result) {
        
        // Type casting and comparison
        if (result == (long) result) {
            return String.valueOf((long) result);  // String.valueOf() conversion
        }
        
        return String.valueOf(result);
    }
    
    // Getter method
    public String getOperationName() {
        return operationName;
    }
}