import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Timer;

public class TaskScheduler extends JFrame {
    private JTextField taskField;
    private JTextField dateTimeField;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private java.util.List<ScheduledTask> scheduledTasks;

    public TaskScheduler() {
        setTitle("Simple Java Scheduler");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        scheduledTasks = new ArrayList<>();

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        taskField = new JTextField();
        dateTimeField = new JTextField("yyyy-MM-dd HH:mm");

        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskField);
        inputPanel.add(new JLabel("Date & Time:"));
        inputPanel.add(dateTimeField);

        JButton addButton = new JButton("Schedule Task");
        inputPanel.add(addButton);

        // Table
        String[] columnNames = {"Task", "DateTime"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(taskTable);

        // Layout
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Add Button Action
        addButton.addActionListener(e -> {
            String taskText = taskField.getText();
            String dateTimeText = dateTimeField.getText();
            try {
                LocalDateTime dateTime = LocalDateTime.parse(dateTimeText, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                scheduledTasks.add(new ScheduledTask(taskText, dateTime));
                tableModel.addRow(new Object[]{taskText, dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))});
                taskField.setText("");
                dateTimeField.setText("yyyy-MM-dd HH:mm");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date/time format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Scheduler Timer
        new Timer(1000, e -> checkTasks()).start();
    }

    private void checkTasks() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        Iterator<ScheduledTask> iterator = scheduledTasks.iterator();
        while (iterator.hasNext()) {
            ScheduledTask task = iterator.next();
            if (task.getDateTime().equals(now)) {
                JOptionPane.showMessageDialog(this, "Scheduled Task: " + task.getTask(), "Task Reminder", JOptionPane.INFORMATION_MESSAGE);
                iterator.remove(); // Remove task after alerting
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskScheduler().setVisible(true));
    }

    static class ScheduledTask {
        private final String task;
        private final LocalDateTime dateTime;

        public ScheduledTask(String task, LocalDateTime dateTime) {
            this.task = task;
            this.dateTime = dateTime;
        }

        public String getTask() {
            return task;
        }

        public LocalDateTime getDateTime() {
            return dateTime;
        }
    }
}
