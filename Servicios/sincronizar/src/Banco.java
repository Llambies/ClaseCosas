import java.util.concurrent.Semaphore;

public class Banco implements Runnable {
    int saldo = 2000;
    Semaphore cajero = new Semaphore(1);


    public void run() {
        try {
            cajero.acquire();
            System.out.println("Ingreso");
            saldo++;
            Thread.sleep(Math.round(Math.random() * 1000));
            System.out.println("Ingresado");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cajero.release();
        }

    }
}
