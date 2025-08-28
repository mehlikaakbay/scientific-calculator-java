package calculator;

public class SpecialOperation extends Operation {
    private String operationType; 
    
    public SpecialOperation(String operationType) {
        super("Special");  // Super constructor call
        this.operationType = operationType;
    }
    
    @Override
    public double calculate(double... operands) throws ArithmeticException {
        
        if (operands.length != 1) {
            throw new ArithmeticException("Special operations require exactly 1 operand");
        }
        
        double value = operands[0];
        
        return switch (operationType) {
            case "√" -> {
                
                if (value < 0) {
                    throw new ArithmeticException("Square root of negative number: " + value);
                }
                yield Math.sqrt(value);  // Math.sqrt() - square root
            }
            
            case "n!" -> {
                
                int n = (int) value;  // Type casting double to int
                
                // Check if casting lost precision
                if (n != value) {
                    throw new ArithmeticException("Factorial requires integer input: " + value);
                }
                
                if (n < 0) {
                    throw new ArithmeticException("Factorial of negative number: " + n);
                }
                
                if (n > 20) {
                    throw new ArithmeticException("Factorial too large: " + n + " (maximum is 20)");
                }
                
                // For loop for factorial calculation
                long result = 1;
                for (int i = 2; i <= n; i++) {
                    result *= i;  // Compound assignment
                }
                yield (double) result;  // Type casting long to double
            }
            
            case "%" -> {
                
                yield value / 100.0;  // Division
            }
            
            case "+/-" -> {
               
                yield -value;  // Unary minus operator
            }
            
            default -> throw new ArithmeticException("Unknown special operation: " + operationType);
        };
    }
    
    @Override
    public String toString() {
        return "SpecialOperation{" + operationType + "}";
    }
}