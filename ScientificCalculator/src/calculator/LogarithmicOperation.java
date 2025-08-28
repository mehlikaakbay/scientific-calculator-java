package calculator;

// Class inheritance - extends Operation
public class LogarithmicOperation extends Operation {
    private String logType; 
    
    // Constructor with super() call
    public LogarithmicOperation(String logType) {
        super("Logarithmic");
        this.logType = logType;
    }
    
    // Method override
    @Override
    public double calculate(double... operands) throws ArithmeticException {
        
        if (operands.length != 1) {
            throw new ArithmeticException("Logarithmic operations require exactly 1 operand");
        }
        
        double value = operands[0];
        
        // Input validation
        if (value <= 0) {
            throw new ArithmeticException("Logarithm of non-positive number: " + value);
        }
        
        // Switch expression with Math class methods
        return switch (logType) {
            case "log" -> Math.log10(value);  // Math.log10() - base 10 logarithm
            case "ln" -> Math.log(value);     // Math.log() - natural logarithm
            default -> throw new ArithmeticException("Unknown logarithmic function: " + logType);
        };
    }
    
    // toString() override
    @Override
    public String toString() {
        return "LogarithmicOperation{" + logType + "}";
    }
}