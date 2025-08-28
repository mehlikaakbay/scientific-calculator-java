package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private final JTextField display;
    private final CalculatorEngine engine;

    @SuppressWarnings("serial")
    public CalculatorGUI() {
        engine = new CalculatorEngine();  // Initialize calculator engine

        // JFrame setup methods
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);  // Center window
        setLayout(new BorderLayout());

        // JTextField creation and configuration
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.PLAIN, 24));  // Font constructor
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);  // BorderLayout positioning

        // Input/Action map setup for keyboard shortcuts
        InputMap im = display.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = display.getActionMap();

        // String.toCharArray() to iterate characters
        String keys = "0123456789+-*/.=c";
        for (char key : keys.toCharArray()) {
            // KeyStroke.getKeyStroke() for key binding
            im.put(KeyStroke.getKeyStroke(key), String.valueOf(key));
            // Anonymous AbstractAction class
            am.put(String.valueOf(key), new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    handleKeyInput(String.valueOf(key));
                }
            });
        }

        // Special key bindings
        im.put(KeyStroke.getKeyStroke("ENTER"), "EVALUATE");
        am.put("EVALUATE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                evaluate();
            }
        });

        im.put(KeyStroke.getKeyStroke("BACK_SPACE"), "BACK");
        am.put("BACK", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String txt = getDisplayText();
                // String.isEmpty() and substring() methods
                if (!txt.isEmpty()) {
                    setDisplayText(txt.substring(0, txt.length() - 1));
                }
            }
        });

        // JPanel with GridLayout for button arrangement
        JPanel panel = new JPanel(new GridLayout(7, 5, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // String array for button labels
        String[] buttons = {
            "1", "2", "3", "/", "sin",
            "cos", "4", "5", "6", "tan",
            "log", "*", "7", "8", "9",
            "asin", "acos", "atan", "ln", "0",
            "-", "√", "x^y", "e^x", "10^x",
            ".", "+", "=", "n!", "C",
            "+/-", "%", "Hist", "MS", "MR",
            "M+", "M-"
        };

        // Enhanced for loop to create buttons
        for (String text : buttons) {
            JButton btn = new JButton(text);  // JButton constructor
            btn.setFont(new Font("Arial", Font.PLAIN, 18));
            // Lambda expression for ActionListener
            btn.addActionListener(e -> handleButton(text));
            panel.add(btn);  // Add to panel
        }

        add(panel, BorderLayout.CENTER);  // Add panel to frame
    }

    // Getter method for display text
    private String getDisplayText() {
        return display.getText();
    }

    // Setter method for display text
    private void setDisplayText(String text) {
        display.setText(text);
    }

    // Clear display method
    private void clearDisplay() {
        display.setText("");
    }

    // Handle keyboard input
    private void handleKeyInput(String key) {
        switch (key) {
            case "=" -> evaluate();
            case "c" -> clearDisplay();
            // String concatenation
            default -> display.setText(display.getText() + key);
        }
    }

    // Handle button clicks with try-catch for exception handling
    private void handleButton(String cmd) {
        try {
            switch (cmd) {
                case "C" -> clearDisplay();
                case "=" -> evaluate();
                // Method calls with parameters
                case "sin", "cos", "tan" -> applyUnary("trigonometric", cmd, false);
                case "asin", "acos", "atan" -> applyUnary("trigonometric", cmd.substring(1), true);
                case "log", "ln" -> applyUnary("logarithmic", cmd);
                case "e^x", "10^x" -> applyUnary("exponential", cmd);
                case "√", "n!", "%", "+/-" -> applyUnary("special", cmd);
                case "x^y" -> display.setText(display.getText() + "^");
                // Double.parseDouble() for string to double conversion
                case "MS" -> engine.storeInMemory(Double.parseDouble(getDisplayText()));
                case "MR" -> setDisplayText(String.valueOf(engine.recallFromMemory()));
                case "M+" -> engine.addToMemory(Double.parseDouble(getDisplayText()));
                case "M-" -> engine.subtractFromMemory(Double.parseDouble(getDisplayText()));
                case "Hist" -> showHistory();
                default -> display.setText(display.getText() + cmd);
            }
        } catch (NumberFormatException ex) {
            setDisplayText("Input Error");
        } catch (ArithmeticException ex) {
            setDisplayText("Math Error");
        } catch (Exception ex) {
            setDisplayText("Error");
        }
    }

    // Method overloading - same name, different parameters
    private void applyUnary(String type, String function) {
        applyUnary(type, function, false);
    }

    // Apply unary operations
    private void applyUnary(String type, String function, boolean isInverse) {
        // Ternary operator for conditional assignment
        Operation op = type.equals("trigonometric")
                ? engine.createOperation(type, function, String.valueOf(isInverse))
                : engine.createOperation(type, function);
        String result = engine.executeOperation(op, Double.parseDouble(getDisplayText()));
        setDisplayText(result);
    }

    // Evaluate expressions
    private void evaluate() {
        String expr = getDisplayText().trim();  // String.trim() removes whitespace
        if (expr.isEmpty()) return;
        try {
            // String.contains() to check for characters
            if (expr.contains("^")) {
                // String.split() with regex
                String[] parts = expr.split("\\^");
                double base = Double.parseDouble(parts[0]);
                double exp = Double.parseDouble(parts[1]);
                Operation op = engine.createOperation("arithmetic", "^");
                String result = engine.executeOperation(op, base, exp);
                setDisplayText(result);
                return;
            }
            // String.matches() with regex pattern
            if (expr.matches(".*[+\\-*/].*")) {
                String[] parts = expr.split("(?<=[+\\-*/])|(?=[+\\-*/])");
                double a = Double.parseDouble(parts[0].trim());
                double b = Double.parseDouble(parts[2].trim());
                String operator = parts[1];
                Operation op = engine.createOperation("arithmetic", operator);
                String result = engine.executeOperation(op, a, b);
                setDisplayText(result);
            }
        } catch (Exception e) {
            setDisplayText("Error");
        }
    }

    // Show calculation history using JOptionPane
    private void showHistory() {
        JOptionPane.showMessageDialog(this,
                engine.isHistoryEmpty() ? "No history yet" : engine.getHistory(),
                "Calculation History",
                JOptionPane.INFORMATION_MESSAGE);
    }
}