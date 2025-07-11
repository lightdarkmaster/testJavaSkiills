import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskScheduler extends JFrame {
    private JTextField taskField;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private java.util.List<ScheduledTask> scheduledTasks;

    private JComboBox<Integer> yearCombo, monthCombo, dayCombo, hourCombo, minuteCombo;

    public TaskScheduler() {
        setTitle("🗓 Task Scheduler");
        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        scheduledTasks = new ArrayList<>();

        // Root panel
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout(10, 10));
        rootPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(rootPanel);

        // Header
        JLabel heading = new JLabel("🗓 Java Task Scheduler", JLabel.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        rootPanel.add(heading, BorderLayout.NORTH);

        // === Input Panel ===
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Schedule a Task"));
        rootPanel.add(inputPanel, BorderLayout.WEST);

        // Task Input
        JPanel taskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel taskLabel = new JLabel("Task:");
        taskField = new JTextField(20);
        taskPanel.add(taskLabel);
        taskPanel.add(taskField);
        inputPanel.add(taskPanel);

        // Date-Time Panel
        JPanel dateTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        yearCombo = new JComboBox<>();
        monthCombo = new JComboBox<>();
        dayCombo = new JComboBox<>();
        hourCombo = new JComboBox<>();
        minuteCombo = new JComboBox<>();

        int currentYear = LocalDateTime.now().getYear();
        for (int y = currentYear; y <= currentYear + 5; y++) yearCombo.addItem(y);
        for (int m = 1; m <= 12; m++) monthCombo.addItem(m);
        for (int d = 1; d <= 31; d++) dayCombo.addItem(d);
        for (int h = 0; h <= 23; h++) hourCombo.addItem(h);
        for (int m = 0; m <= 59; m++) minuteCombo.addItem(m);

        dateTimePanel.add(new JLabel("Year:"));
        dateTimePanel.add(yearCombo);
        dateTimePanel.add(new JLabel("Month:"));
        dateTimePanel.add(monthCombo);
        dateTimePanel.add(new JLabel("Day:"));
        dateTimePanel.add(dayCombo);
        dateTimePanel.add(new JLabel("Hour:"));
        dateTimePanel.add(hourCombo);
        dateTimePanel.add(new JLabel("Minute:"));
        dateTimePanel.add(minuteCombo);
        inputPanel.add(dateTimePanel);

        // Schedule Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addButton = new JButton("➕ Schedule Task");
        addButton.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(addButton);
        inputPanel.add(buttonPanel);

        // === Table Panel ===
        String[] columnNames = {"Task", "Scheduled DateTime"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(taskTable);
        rootPanel.add(tableScrollPane, BorderLayout.CENTER);

        // === Add Button Action ===
        addButton.addActionListener(e -> {
            try {
                String taskText = taskField.getText().trim();
                if (taskText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Task cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int year = (int) yearCombo.getSelectedItem();
                int month = (int) monthCombo.getSelectedItem();
                int day = (int) dayCombo.getSelectedItem();
                int hour = (int) hourCombo.getSelectedItem();
                int minute = (int) minuteCombo.getSelectedItem();

                LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);

                if (dateTime.isBefore(LocalDateTime.now())) {
                    JOptionPane.showMessageDialog(this, "Scheduled time must be in the future.", "Invalid Time", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                scheduledTasks.add(new ScheduledTask(taskText, dateTime));
                tableModel.addRow(new Object[]{taskText, dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))});
                taskField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid date/time.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // === Timer ===
        new javax.swing.Timer(1000, e -> checkTasks()).start();
    }

    private void checkTasks() {
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        Iterator<ScheduledTask> iterator = scheduledTasks.iterator();
        while (iterator.hasNext()) {
            ScheduledTask task = iterator.next();
            if (task.getDateTime().equals(now)) {
                JOptionPane.showMessageDialog(this, "Scheduled Task: " + task.getTask(), "Task Reminder", JOptionPane.INFORMATION_MESSAGE);
                iterator.remove(); // Remove after alert
            }
        }
    }

    public void checkSchedule(){
        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
        Iterator<ScheduledTask> iterator = scheduledTasks.iterator();
        while(iterator.hasNext()){
            ScheduledTask task = iterator.next();
            if(task.getDateTime().equals(now)){
                JOptionPane.showMessageDialog(this, "Scheduled Task: " + task.getTask(), "Task Schedule", JOptionPane.INFORMATION_MESSAGE);
                iterator.remove();
            }
        }
    }
    //Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskScheduler().setVisible(true));
    }

    //static class scheduletask
    static class ScheduledTask {
        private final String task;
        private final LocalDateTime dateTime;

        public ScheduledTask(String task, LocalDateTime dateTime) {
            this.task = task;
            this.dateTime = dateTime;
        }
        //get task
        public String getTask() {
            return task;
        }

        //Get time function.
        public LocalDateTime getDateTime() {
            return dateTime;
        }
    }
}