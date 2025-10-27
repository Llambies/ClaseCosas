public class Main {
    public static void main(String[] args) {
        int numMonos = 5;
        for (int i = 0; i < numMonos; i++) {
            DosDirMono mono = new DosDirMono(i,"Derecha");
            Thread thread = new Thread(mono);
            thread.start();
        }
        for (int i = 0; i < numMonos; i++) {
            DosDirMono mono = new DosDirMono(i,"Izquierda");
            Thread thread = new Thread(mono);
            thread.start();
        }
    }

    static void ejemplo1(){
        int numMonos = 5;
        for (int i = 0; i < numMonos; i++) {
            UnaDirMono mono = new UnaDirMono(i);
            Thread thread = new Thread(mono);
            thread.start();
        }
    }
}