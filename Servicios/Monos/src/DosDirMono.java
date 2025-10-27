import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class DosDirMono implements Runnable {
    static Semaphore puente = new Semaphore(1);

    static Semaphore controlAcceso = new Semaphore(1);
    static List<DosDirMono> lista = new LinkedList<>();
    static int posicion = 0;

    int id;
    String direccion;

    @Override
    public void run() {
        try {
            System.out.println("Mono quiere pasar" + direccion + " " + id);
            controlAcceso.acquire();
            lista.add(this);
            controlAcceso.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Mono " + direccion + " " + id + " esperando");
        while (true) {
            try {
                controlAcceso.acquire();
                if (lista.get(posicion) == this){
                    controlAcceso.release();
                    break;
                }
                controlAcceso.release();
                Thread.sleep(Math.round(Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Mono " + direccion + " " + id + " le toca");
        try {
            puente.acquire();
            System.out.println("Mono " + direccion + " " + id + " cruza");
            Thread.sleep(Math.round(Math.random() * 1000));
            try {
                controlAcceso.acquire();
                posicion++;
                controlAcceso.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("Mono " + direccion + " " + id + " ha cruzado");
            puente.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public DosDirMono(int i, String direccion) {
        id = i;
        this.direccion = direccion;
    }
}
