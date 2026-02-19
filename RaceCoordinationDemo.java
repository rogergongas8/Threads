
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class RaceCoordinationDemo {
    public static void main(String[] args) throws InterruptedException {
        int runners = 5;
        CyclicBarrier startBarrier = new CyclicBarrier(runners,
                () -> System.out.println("RGG - PISTOLAZO! Todos salen a la vez.\n"));
        CountDownLatch finishLatch = new CountDownLatch(runners);
        Random rnd = new Random();
        for (int i = 1; i <= runners; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    System.out.println("RGG - Corredor " + id + " listo en la salida.");
                    startBarrier.await(); // esperan todos y salen juntos
                    // "Correr"
                    int runMs = 400 + rnd.nextInt(600);
                    TimeUnit.MILLISECONDS.sleep(runMs);
                    System.out.println("RGG - Corredor " + id + " termina en " + runMs + " ms");
                } catch (Exception e) {
                    System.out.println("RGG - Corredor " + id + " error: " + e);
                } finally {
                    finishLatch.countDown(); // avisar al juez
                }
            }, "Runner-" + id).start();
        }
        System.out.println("RGG - Juez: esperando a que terminen...");
        finishLatch.await();
        System.out.println("\nRGG - Juez: carrera finalizada. Todos han llegado.");
    }
}