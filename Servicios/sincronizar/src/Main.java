//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Puente parkin = new Puente();

        for (int i = 0; i < 10; i++) {
            Thread threadIzq = new Thread(parkin, "Mono (Izq) " + i);
            Thread threadDer = new Thread(parkin, "Mono (Der) " + i);
            threadIzq.start();
            threadDer.start();
        }

    }
}