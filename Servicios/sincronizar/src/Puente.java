import java.util.concurrent.Semaphore;

public class Puente implements Runnable {
    Semaphore puente = new Semaphore(1);
    public void run() {
        String nombre = Thread.currentThread().getName();
        try {
            Thread.sleep(Math.round(Math.random() * 5000));
            puente.acquire();
            System.out.println("Ida: " + nombre);
            Thread.sleep(Math.round(Math.random() * 1000));
            puente.release();
            Thread.sleep(Math.round(Math.random() * 5000));
            try {
                puente.acquire();
                System.out.println("Vuelta: " + nombre);
                Thread.sleep(Math.round(Math.random() * 1000));
                puente.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
