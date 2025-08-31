import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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

        // Input panel here..
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

        // Currency formatter
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));

        // Initial contribution rates (modifiable)
        final double[] sssRate = {0.05};
        final double[] philHealthRate = {0.05};

        // Calculate action
        calculateButton.addActionListener(e -> {
            try {
                double baseSalary = Double.parseDouble(salaryField.getText());

                double sss = baseSalary * sssRate[0];
                double afterSSS = baseSalary - sss;

                double philHealth = afterSSS * philHealthRate[0];
                double finalSalary = afterSSS - philHealth;

                int perDay = 620;
                int perHour = perDay / 8;

                //Here is the Display Sample..
                String result = "=========== Salary Summary ===========\n" +
                        "Base Salary              : " + format.format(baseSalary) + "\n" +
                        "SSS Contribution (" + (int) (sssRate[0] * 100) + "%)    : " + format.format(sss) + "\n" +
                        "Salary after SSS         : " + format.format(afterSSS) + "\n" +
                        "PhilHealth Contribution (" + (int) (philHealthRate[0] * 100) + "%): " + format.format(philHealth) + "\n" +
                        "Salary Per Hour (₱)      : " + perHour + "\n" +
                        "Salary Per Day (₱)       : " + perDay + "\n" +
                        "Final Take-Home Salary   : " + format.format(finalSalary) + "\n" +
                        "======================================";

                resultArea.setText(result);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "❗ Please enter a valid number for the base salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Create Menu Bar for the options..
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem setRatesItem = new JMenuItem("Set Contribution Rates");

        setRatesItem.addActionListener(event -> {
            JTextField sssField = new JTextField(String.valueOf(sssRate[0] * 100));
            JTextField philHealthField = new JTextField(String.valueOf(philHealthRate[0] * 100));

            JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
            panel.add(new JLabel("SSS Contribution (%)"));
            panel.add(sssField);
            panel.add(new JLabel("PhilHealth Contribution (%)"));
            panel.add(philHealthField);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Set Contribution Rates",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    double newSSS = Double.parseDouble(sssField.getText()) / 100;
                    double newPhil = Double.parseDouble(philHealthField.getText()) / 100;

                    if (newSSS < 0 || newPhil < 0) throw new NumberFormatException();

                    sssRate[0] = newSSS;
                    philHealthRate[0] = newPhil;

                    JOptionPane.showMessageDialog(frame, "Rates updated successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid percentage values.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

//        notes: this will assume that there are no deductions in your salary, you don't have late etc.

        //options menu
        optionsMenu.add(setRatesItem);
        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        // Final step: show window for the frame to show.
        frame.setVisible(true);
    }

    public static void getStatus(){
        boolean online = false;
        String status = "Active";

        if(online){
            System.out.println(status);
            System.out.println(status);
        }else{
            System.out.println("Not- Active");
        }
    }
}
