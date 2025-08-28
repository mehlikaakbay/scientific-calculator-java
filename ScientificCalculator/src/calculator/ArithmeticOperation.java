package calculator;


public class ArithmeticOperation extends Operation {
    private String operator;
    
 // Constructor - calls super() to initialize parent class
    public ArithmeticOperation(String operator) {
        super("Arithmetic"); 
        this.operator = operator;
    }
    
 // Override abstract method from Operation class
    @Override
    public double calculate(double... operands) throws ArithmeticException {
    	// Check array length for validation
        if (operands.length != 2) {
            throw new ArithmeticException("Arithmetic operations require exactly 2 operands");
        }
        
        double a = operands[0]; 
        double b = operands[1]; 
        
     // Switch expression for operator handling
        return switch (operator) {
            case "+" -> a + b;                    //Addition
            case "-" -> a - b;                    // Subtraction
            case "*" -> a * b;                    //Multiplication
            case "/" -> {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield a / b;                      // Division with yield keyword
            }
            case "^" -> Math.pow(a, b);          // Power using Math.pow()
            default -> throw new ArithmeticException("Unknown arithmetic operator: " + operator);
        };
    }
    
    // Override toString() for object representation
    @Override
    public String toString() {
        return "ArithmeticOperation{operator='" + operator + "'}";
    }
}
