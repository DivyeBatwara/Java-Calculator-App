import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {

    // Components for the calculator
    private JTextField displayField;
    private double num1, num2, result;
    private char operator;
    private boolean isNewCalculation = true;

    // Constructor to set up the GUI
    public Calculator() {
        // --- Frame Setup ---
        setTitle("Simple Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Gaps between components
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        // --- Display Field ---
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 32));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10)); // 4x4 grid with 10px gaps
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // --- Button Creation ---
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.setFocusable(false); // Improves user experience
            button.addActionListener(this);
            
            // Set colors for operators and the 'C' button
            if ("C".equals(label)) {
                button.setBackground(new Color(255, 100, 100)); // Reddish color for Clear
                button.setForeground(Color.WHITE);
            } else if ("=+-*/".contains(label)) {
                button.setBackground(new Color(173, 216, 230)); // Light blue for operators
            }
            
            buttonPanel.add(button);
        }

        // --- Add Components to Frame ---
        add(displayField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        // Display the window
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    // --- Event Handling Logic ---
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handle number inputs
        if ("0123456789".contains(command)) {
            if (isNewCalculation) {
                displayField.setText("");
                isNewCalculation = false;
            }
            displayField.setText(displayField.getText() + command);
        } 
        // Handle clear button
        else if ("C".equals(command)) {
            displayField.setText("");
            num1 = 0;
            num2 = 0;
            operator = '\0'; // Null character
            isNewCalculation = true;
        } 
        // Handle equals button
        else if ("=".equals(command)) {
            try {
                num2 = Double.parseDouble(displayField.getText());
                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 == 0) {
                            displayField.setText("Error: Div by zero");
                            return; // Exit method to prevent further processing
                        }
                        result = num1 / num2;
                        break;
                }
                // Display integer result without ".0"
                if (result == (long) result) {
                    displayField.setText(String.format("%d", (long) result));
                } else {
                    displayField.setText(String.format("%s", result));
                }
                isNewCalculation = true;
            } catch (NumberFormatException ex) {
                displayField.setText("Error");
                isNewCalculation = true;
            }
        } 
        // Handle operator buttons
        else { // For +, -, *, /
            if (!displayField.getText().isEmpty()) {
                num1 = Double.parseDouble(displayField.getText());
                operator = command.charAt(0);
                isNewCalculation = true;
            }
        }
    }

    // --- Main Method ---
    public static void main(String[] args) {
        // Run the GUI construction in the Event-Dispatch-Thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Calculator();
            }
        });
    }
}