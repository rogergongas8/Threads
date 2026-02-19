
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentListDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("RGG - === Caso 1: ArrayList (posible error) ===");
        demoWithList(new ArrayList<>());
        System.out.println("\nRGG - === Caso 2: CopyOnWriteArrayList (correcto) ===");
        demoWithList(new CopyOnWriteArrayList<>());
    }

    static void demoWithList(List<Integer> list) throws InterruptedException {
        int threads = 20;
        int addsPerThread = 10_000;
        Thread[] ts = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            final int id = i;
            ts[i] = new Thread(() -> {
                for (int j = 0; j < addsPerThread; j++) {
                    list.add(id * addsPerThread + j);
                }
            }, "Adder-" + i);
        }
        for (Thread t : ts)
            t.start();
        for (Thread t : ts)
            t.join();
        System.out.println("RGG - Tamaño esperado = " + (threads * addsPerThread));
        System.out.println("RGG - Tamaño real     = " + list.size());
    }
}