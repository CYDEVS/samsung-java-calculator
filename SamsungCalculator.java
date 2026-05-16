import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SamsungCalculator extends JFrame implements ActionListener {
    
    // UI Elements
    private JTextField display;
    private JPanel panel;
    
    // Calculator State Variables
    private double num1 = 0;
    private double num2 = 0;
    private double result = 0;
    private char operator;

    public SamsungCalculator() {
        // 1. Setup the window frame
        setTitle("Cyril Calculator");
        setSize(380, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Create the calculator screen display
        display = new JTextField("0");
        display.setFont(new Font("Cartoon", Font.PLAIN, 40));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(display, BorderLayout.NORTH);

        // 3. Create the grid layout for calculator buttons (5 rows, 4 columns)
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // The button layout layout template
        String[] buttons = {
            "C", "( )", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        // 4. Generate buttons dynamically using a loop
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setFocusable(false);
            button.addActionListener(this); // Tell button to listen for clicks

            // Samsung style color styling matching
            if (text.equals("÷") || text.equals("×") || text.equals("-") || text.equals("+") || text.equals("=")) {
                button.setBackground(new Color(239, 143, 61)); // Orange math operator color
                button.setForeground(Color.GRAY);
            } else if (text.equals("C") || text.equals("%") || text.equals("( )")) {
                button.setBackground(new Color(220, 220, 220)); // Light grey helper keys
            } else {
                button.setBackground(new Color(245, 245, 245)); // Off-white number pad keys
            }

            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Case A: Clear Screen Button
        if (command.equals("C")) {
            display.setText("0");
            num1 = 0;
            num2 = 0;
            result = 0;
        } 
        // Case B: Calculate Equals execution block
        else if (command.equals("=")) {
            num2 = Double.parseDouble(display.getText());
            
            switch (operator) {
                case '+': result = num1 + num2; break;
                case '-': result = num1 - num2; break;
                case '×': result = num1 * num2; break;
                case '÷': 
                    if (num2 == 0) {
                        display.setText("Cannot divide by 0");
                        return;
                    }
                    result = num1 / num2; 
                    break;
            }
            
            // Format to strip out trailing .0 if integer
            if (result % 1 == 0) {
                display.setText(String.valueOf((int) result));
            } else {
                display.setText(String.valueOf(result));
            }
            num1 = result; // Allows chaining operations
        } 
        // Case C: Read Operator math commands
        else if (command.equals("+") || command.equals("-") || command.equals("×") || command.equals("÷")) {
            num1 = Double.parseDouble(display.getText());
            operator = command.charAt(0);
            display.setText("0");
        } 
        // Case D: Append Numbers onto the active screen layout
        else {
            if (display.getText().equals("0") && !command.equals(".")) {
                display.setText(command);
            } else {
                display.setText(display.getText() + command);
            }
        }
    }

    public static void main(String[] args) {
        // Run the layout build construction thread
        new SamsungCalculator();
    }
}