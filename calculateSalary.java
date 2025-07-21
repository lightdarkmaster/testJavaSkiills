import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

public class calculateSalary {
    public static void main(String[] args) {
        // Set system look and feel for modern style
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set Look and Feel");
        }

        // Main frame
        JFrame frame = new JFrame("💼 Salary Calculator");
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen

        // Use GridBagLayout for full center alignment
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.decode("#f4f4f4"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.decode("#f4f4f4"));

        JLabel titleLabel = new JLabel("Salary Calculator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel("Enter Base Salary (₱):");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField salaryField = new JTextField(10);
        salaryField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        salaryField.setMaximumSize(new Dimension(300, 40));
        salaryField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        calculateButton.setBackground(new Color(233, 244, 155));
        calculateButton.setForeground(Color.BLUE);
        calculateButton.setFocusPainted(false);
        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        inputPanel.add(titleLabel);
        inputPanel.add(Box.createVerticalStrut(20));
        inputPanel.add(label);
        inputPanel.add(Box.createVerticalStrut(5));
        inputPanel.add(salaryField);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(calculateButton);

        // Result area
        JTextArea resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        resultArea.setMargin(new Insets(10, 10, 10, 10));
        resultArea.setBackground(Color.WHITE);
        resultArea.setForeground(Color.DARK_GRAY);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        resultArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Output panel
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.setBackground(Color.decode("#f4f4f4"));
        outputPanel.add(scrollPane);

        // Add panels to container (centered)
        container.add(inputPanel, gbc);
        gbc.gridy++;
        container.add(outputPanel, gbc);

        // Add container to frame
        frame.add(container);
        frame.setVisible(true);

        // Currency formatter
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));

        // Calculate action
        calculateButton.addActionListener(e -> {
            try {
                double baseSalary = Double.parseDouble(salaryField.getText());

                double sss = baseSalary * 0.05;
                double afterSSS = baseSalary - sss;

                double philHealth = afterSSS * 0.05;
                double finalSalary = afterSSS - philHealth;

                int perDay = 620;
                int perHour = perDay / 8;

                StringBuilder result = new StringBuilder();
                result.append("=========== Salary Summary ===========\n");
                result.append("Base Salary              : ").append(format.format(baseSalary)).append("\n");
                result.append("SSS Contribution (5%)    : ").append(format.format(sss)).append("\n");
                result.append("Salary after SSS         : ").append(format.format(afterSSS)).append("\n");
                result.append("PhilHealth Contribution  : ").append(format.format(philHealth)).append("\n");
                result.append("Final Take-Home Salary   : ").append(format.format(finalSalary)).append("\n");
                result.append("Salary Per Hour (₱)      : ").append(perHour).append("\n");
                result.append("Salary Per Day (₱)      : ").append(perDay).append("\n");
                result.append("======================================");

                resultArea.setText(result.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "❗ Please enter a valid number for the base salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
