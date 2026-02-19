
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        CompletableFuture<Void> pipeline = descargarDatosAsync()
                .thenApply(CompletableFutureDemo::procesarDatos) // transforma
                .thenAccept(CompletableFutureDemo::mostrarResultado) // consume
                .exceptionally(ex -> { // errores
                    System.out.println(" RGG - Error en pipeline: " + ex);
                    return null;
                });
        // En un programa real, el main no debería acabar antes.
        // Aquí hacemos join() solo para que se vea el resultado en consola.
        pipeline.join();
        System.out.println("RGG - Pipeline completado.");
    }

    static CompletableFuture<String> descargarDatosAsync() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(400);
            System.out.println("RGG -Descarga completada (" + Thread.currentThread().getName() + ")");
            return "RGG_DATA: A, B, C";
        });
    }

    static String procesarDatos(String raw) {
        sleep(300);
        System.out.println("RGG - Procesamiento completado (" + Thread.currentThread().getName() + ")");
        return raw.toUpperCase() + " | OK";
    }

    static void mostrarResultado(String processed) {
        sleep(100);
        System.out.println("RGG - Resultado (" + Thread.currentThread().getName() + "): " + processed);
    }

    static void sleep(long ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}