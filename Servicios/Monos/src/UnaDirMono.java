import java.util.concurrent.Semaphore;

public class UnaDirMono implements Runnable {
    static Semaphore puente = new Semaphore(1);
    int id;

    @Override
    public void run() {
        try {
            puente.acquire();
            System.out.println("Mono " + id + " cruza");
            Thread.sleep(Math.round(Math.random() * 5000));
            puente.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public UnaDirMono(int i) {
        id = i;
    }
}
