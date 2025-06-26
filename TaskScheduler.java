import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class TaskScheduler {

    // Thread-safe map for task results
    private static final ConcurrentMap<String, TaskResult> taskResults = new ConcurrentHashMap<>();
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Task Scheduler Started");

        // Define tasks
        for (int i = 0; i < 10; i++) {
            Task task = new Task("Task-" + i, () -> {
                int rand = ThreadLocalRandom.current().nextInt(1, 10);
                if (rand <= 3) throw new TaskExecutionException("Random failure.");
                Thread.sleep(rand * 100L);
                return "Completed after " + rand + " units.";
            });

            executor.submit(() -> runWithRetry(task, 3));
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        generateReport();

        System.out.println("All tasks completed. Report generated.");
    }

    // Retry logic for tasks
    private static void runWithRetry(Task task, int maxRetries) {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                attempt++;
                String result = task.callable.call();
                taskResults.put(task.id, new TaskResult(task.name, result, "Success", LocalDateTime.now()));
                return;
            } catch (Exception e) {
                if (attempt == maxRetries) {
                    taskResults.put(task.id, new TaskResult(task.name, e.getMessage(), "Failed", LocalDateTime.now()));
                }
            }
        }
    }

    // Write taskResults to JSON
    private static void generateReport() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("task_report.json")) {
            gson.toJson(taskResults, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inner classes

    static class Task {
        String id;
        String name;
        Callable<String> callable;

        Task(String name, Callable<String> callable) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.callable = callable;
        }
    }

    static class TaskResult {
        String taskName;
        String result;
        String status;
        LocalDateTime timestamp;

        TaskResult(String taskName, String result, String status, LocalDateTime timestamp) {
            this.taskName = taskName;
            this.result = result;
            this.status = status;
            this.timestamp = timestamp;
        }
    }

    static class TaskExecutionException extends Exception {
        TaskExecutionException(String message) {
            super(message);
        }
    }
}
