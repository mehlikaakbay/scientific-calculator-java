package calculator;

public class ExponentialOperation extends Operation {
    private String expType; 
    
    public ExponentialOperation(String expType) {
        super("Exponential");  // Super constructor
        this.expType = expType;
    }
    
    @Override
    public double calculate(double... operands) throws ArithmeticException {
        
        if (operands.length != 1) {
            throw new ArithmeticException("Exponential operations require exactly 1 operand");
        }
        
        double value = operands[0];
        
        // Overflow prevention
        if (value > 700) { 
            throw new ArithmeticException("Exponential overflow: exponent too large");
        }
        
        // Switch with Math exponential functions
        double result = switch (expType) {
            case "e^x" -> Math.exp(value);        // Math.exp() - e^x
            case "10^x" -> Math.pow(10, value);   // Math.pow() - power function
            default -> throw new ArithmeticException("Unknown exponential function: " + expType);
        };
        
        // Double.isInfinite() and Double.isNaN() for validation
        if (Double.isInfinite(result)) {
            throw new ArithmeticException("Exponential overflow: result is infinite");
        }
        if (Double.isNaN(result)) {
            throw new ArithmeticException("Exponential error: result is not a number");
        }
        
        return result;
    }
    
    @Override
    public String toString() {
        return "ExponentialOperation{" + expType + "}";
    }
}