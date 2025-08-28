package calculator;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater() for thread-safe GUI creation
        SwingUtilities.invokeLater(() -> {
            try {
                CalculatorGUI calculator = new CalculatorGUI();  // Object instantiation
                calculator.setVisible(true);  // Show GUI
            } catch (Exception e) {
                // JOptionPane for error dialog
                JOptionPane.showMessageDialog(null,
                    "Calculator initialization failed:\n" + e.getMessage(),
                    "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);  // System.exit() to terminate program
            }
        });
    }
}
