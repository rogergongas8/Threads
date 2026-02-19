
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            for (int i = 1; i <= 10; i++) {
                System.out.println("RGG - Tarea " + i + " creada");
                final int taskId = i;
                executor.submit(() -> {
                    String tname = Thread.currentThread().getName();
                    System.out.println("RGG - Tarea " + taskId + " ejecutada por " + tname);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("RGG - Tarea " + taskId + " interrumpida");
                    }
                });
            }
        } finally {
            executor.shutdown(); // no acepta nuevas tareas
        }
        // Espera cierre limpio
        try {
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // fuerza
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("RGG - Executor cerrado correctamente.");
    }
}