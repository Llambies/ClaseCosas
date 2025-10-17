import java.util.concurrent.Semaphore;

public class Parkin implements Runnable {
    int dinero = 0;
    Semaphore plazas = new Semaphore(3);
    Semaphore pagador = new Semaphore(1);
    public void run() {
        String nombre = Thread.currentThread().getName();
        try {
            plazas.acquire();
            System.out.println("Aparco " + nombre);
            Thread.sleep(Math.round(Math.random() * 5000));
            try {
                pagador.acquire();
                dinero += 15;
                System.out.println(nombre + " pago " + dinero);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                pagador.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            plazas.release();
            System.out.println(nombre + " se va");
        }
    }
}
