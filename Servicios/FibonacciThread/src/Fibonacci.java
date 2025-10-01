public class Fibonacci extends Thread {

    int valor1;
    int valor2;
    int limite;

    public Fibonacci(int limite) {
        this.limite = limite;
        valor1 = 1;
        valor2 = 1;
    }

    @Override
    public void run() {
        if (limite >= 1) {
            System.out.println(valor1);
        }
        if (limite >= 2) {
            System.out.println(valor2);
        }
        for (int i = 2; i < limite; i++) {
            System.out.println(valor1 + valor2);
            int aux = valor1;
            valor1 = valor2;
            valor2 = aux + valor2;
        }
    }
}
