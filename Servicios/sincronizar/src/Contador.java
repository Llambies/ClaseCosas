public class Contador implements Runnable {

    int contador = 0;

    public void run() {
        for (int i = 0; i < 1000; i++) {
            contador++;
        }
    }

    public static void main(String[] args) {
        Contador contador = new Contador();
        Thread thread1 = new Thread(contador);
        Thread thread2 = new Thread(contador);
        thread1.start();
        thread2.start();

        System.out.println("Valor final (no sync): " + contador.contador);
    }
}
