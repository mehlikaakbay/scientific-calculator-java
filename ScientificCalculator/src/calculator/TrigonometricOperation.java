package calculator;

public class TrigonometricOperation extends Operation {
    private String function;    
    private boolean isInverse;  
    
    public TrigonometricOperation(String function, boolean isInverse) {
        super("Trigonometric");  // Super constructor call
        this.function = function;
        this.isInverse = isInverse;
    }
    
    @Override
    public double calculate(double... operands) throws ArithmeticException {
        if (operands.length != 1) {
            throw new ArithmeticException("Trigonometric operations require exactly 1 operand");
        }
        
        double value = operands[0];
        
        if (isInverse) {
           
            // String.equals() for comparison and range validation
            if (function.equals("sin") || function.equals("cos")) {
                if (value < -1 || value > 1) {
                    throw new ArithmeticException("Invalid input for inverse " + function + ": must be between -1 and 1");
                }
            }
            
            // Switch with Math inverse trig functions
            double result = switch (function) {
                case "sin" -> Math.asin(value);   // Math.asin() - arcsine
                case "cos" -> Math.acos(value);   // Math.acos() - arccosine
                case "tan" -> Math.atan(value);   // Math.atan() - arctangent
                default -> throw new ArithmeticException("Unknown inverse trigonometric function: " + function);
            };
            
            // Math.toDegrees() - convert radians to degrees
            return Math.toDegrees(result);
            
        } else {
            double radians = Math.toRadians(value);  // Math.toRadians() - convert degrees to radians
            
            return switch (function) {
                case "sin" -> Math.sin(radians);  // Math.sin()
                case "cos" -> Math.cos(radians);  // Math.cos()
                case "tan" -> {
                    
                    double cosValue = Math.cos(radians);
                    // Math.abs() for absolute value comparison
                    if (Math.abs(cosValue) < 1e-10) { 
                        throw new ArithmeticException("Tangent undefined at " + value + " degrees");
                    }
                    yield Math.tan(radians);  // Math.tan()
                }
                default -> throw new ArithmeticException("Unknown trigonometric function: " + function);
            };
        }
    }
    
    @Override
    public String toString() {
        String prefix = isInverse ? "a" : "";  // Ternary operator
        return "TrigonometricOperation{" + prefix + function + "}";
    }
}