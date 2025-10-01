public class Multiplicador extends Thread{
    int numero;
    public Multiplicador(int numero){
        this.numero = numero;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 10; i++) {
            System.out.println(numero + " * " + i + " = " + numero * i + "");
        }
    }
}
