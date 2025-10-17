import java.util.concurrent.Semaphore;

public class InhodoroPublico implements Runnable {

    final Semaphore cabinas = new Semaphore(2);

    public void run() {
        try {
            cabinas.acquire();
            System.out.println("Defeco");
            Thread.sleep(Math.round(Math.random() * 1000));
            System.out.println("Defeco terminado");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cabinas.release();
        }
    }

}
