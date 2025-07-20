import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

public class calculateSalary {
    public static void main(String[] args) {
        // Create main frame
        JFrame frame = new JFrame("Salary Calculator");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1, 10, 10));

        // Components
        JLabel label = new JLabel("Enter Base Salary (₱):");
        JTextField salaryField = new JTextField();
        JButton calculateButton = new JButton("Calculate");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to frame
        frame.add(label);
        frame.add(salaryField);
        frame.add(calculateButton);
        frame.add(scrollPane);

        // Currency formatter
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));

        // Button action
        calculateButton.addActionListener(e -> {
            try {
                double baseSalary = Double.parseDouble(salaryField.getText());

                double sss = baseSalary * 0.05;
                double afterSSS = baseSalary - sss;

                double philHealth = afterSSS * 0.05;
                double finalSalary = afterSSS - philHealth;

                int perDay = 620;
                int perHour = perDay / 8;

                String result = "";
                result += "SSS Contribution (5%): " + format.format(sss) + "\n";
                result += "Salary after SSS: " + format.format(afterSSS) + "\n\n";
                result += "PhilHealth Contribution (5%): " + format.format(philHealth) + "\n";
                result += "Final Salary: " + format.format(finalSalary) + "\n\n";
                result += "Salary per Hour: ₱" + perHour;

                resultArea.setText(result);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for the base salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Display frame
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }
}
