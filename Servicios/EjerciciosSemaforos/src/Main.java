import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        // Ejercicio1();
        // Ejercicio2();
        // Ejercicio3();
        // Ejercicio4();
        // Ejercicio5();
        // Ejercicio6();
        // Ejercicio7();
        Ejercicio8();
    }

    static void Ejercicio1() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Coche("A")).start();
            new Thread(new Coche("B")).start();
        }
    }

    static void Ejercicio2() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Tren("A", true)).start();
            new Thread(new Tren("B", false)).start();
        }
    }

    static void Ejercicio3() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Barco(true)).start();
            new Thread(new Barco(false)).start();
        }
    }

    static void Ejercicio4() {
        for (int i = 0; i < 12; i++) {
            new Thread(new Persona(true)).start();
            new Thread(new Persona(false)).start();
        }
    }

    static void Ejercicio5() {
        for (int i = 0; i < 100; i++) {
            new Thread(new Robot(1)).start();
            new Thread(new Robot(-1)).start();
        }
    }

    static void Ejercicio6() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Avion(true)).start();
            new Thread(new Avion(false)).start();
        }
    }

    static void Ejercicio7() {
        for (int i = 0; i < 100; i++) {
            new Thread(new TrenCarga()).start();
        }
    }

    static void Ejercicio8() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Ciclista(1)).start();
            new Thread(new Ciclista(-1)).start();
        }
    }
}

class Utils {
    public static int tiempoAleatorio() {
        return (int) (Math.random() * 2000);
    }
}

/**
 * Ejercicio 1
 * Un puente de una sola vía conecta dos pueblos.
 * Los coches llegan desde ambos lados, pero solo uno puede cruzar a la vez.
 * • Si hay un coche cruzando, los demás deben esperar.
 * • Si llegan coches de ambos lados, los de un lado deben esperar a que el
 * puente quede libre.
 * • Usa semáforos para controlar el acceso.
 * Aplicar exclusión mutua con semáforos y evitar cruces simultáneos.
 */

class Coche implements Runnable {
    private static final Semaphore puente = new Semaphore(1);
    private final String puerbloOrigen;
    static int coches = 0;
    int id;

    public Coche(String puerbloOrigen) {
        this.puerbloOrigen = puerbloOrigen;
        this.id = coches;
        coches++;
    }

    @Override
    public void run() {
        try {
            puente.acquire();
            System.out.println("Coche " + id + " cruzando el puente desde " + puerbloOrigen);
            Thread.sleep(Utils.tiempoAleatorio());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            puente.release();
        }
    }
}

/**
 * Ejercicio 2
 * Un túnel ferroviario de vía única conecta dos estaciones.
 * Solo puede haber un tren en el túnel y todos deben circular en la misma
 * dirección.
 * • Si hay trenes cruzando de A → B, los de B → A deben esperar.
 * • Cada tren tarda un tiempo distinto en cruzar.
 * • Algunos trenes (por ejemplo, de pasajeros) pueden tener prioridad sobre los
 * de carga.
 * Controlar el paso de trenes y practicar prioridad y exclusión mutua.
 */
class Tren implements Runnable {
    private static final Semaphore túnel = new Semaphore(1);
    private static final Semaphore controlDeAcceso = new Semaphore(1);
    private static final List<Tren> trenes = new ArrayList<>();

    private static Tren trenActual = null;

    private final String estaciónOrigen;
    static int trenesContador = 0;
    int id;
    boolean esPasajero;

    public Tren(String estaciónOrigen, boolean esPasajero) {
        this.estaciónOrigen = estaciónOrigen;
        this.id = trenesContador;
        trenesContador++;
        this.esPasajero = esPasajero;
    }

    @Override
    public void run() {

        try {
            System.out.println(
                    "Tren " + id +
                            (esPasajero ? " de pasajeros" : " de carga") +
                            " quiere cruzar el túnel desde " + estaciónOrigen);
            controlDeAcceso.acquire();
            if (esPasajero) {
                trenes.addFirst(this);
            } else {
                trenes.add(this);
            }
            controlDeAcceso.release();

            while (trenes.get(0) != this) {
                Thread.sleep(100);
            }

            túnel.acquire();
            System.out.println(
                    "Tren " + id +
                            (esPasajero ? " de pasajeros" : " de carga") +
                            " cruzando el túnel desde " + estaciónOrigen);
            Thread.sleep(Utils.tiempoAleatorio());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                controlDeAcceso.acquire();
                trenActual = trenes.remove(0);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(
                        "Tren " + id +
                                (esPasajero ? " de pasajeros" : " de carga") +
                                " ha cruzado el túnel desde " + estaciónOrigen);
                controlDeAcceso.release();
                túnel.release();
            }
        }
    }
}

/**
 * Ejercicio 3
 * Un canal angosto permite el paso de un solo barco a la vez.
 * Los barcos llegan desde el norte y desde el sur.
 * • Los barcos de una dirección cruzan en grupo (por ejemplo, 3 seguidos).
 * • Luego deben dar paso al otro lado.
 * • Si no hay barcos del lado contrario, el canal sigue libre para la misma
 * dirección.
 * Controlar turnos y alternancia entre grupos usando semáforos.
 */

class Barco implements Runnable {
    private static final Semaphore canal = new Semaphore(1);
    private static final Semaphore controlDeAcceso = new Semaphore(1);

    private static int barcosNorte = 0;
    private static int barcosSur = 0;

    static int barcosContador = 0;
    int id;
    boolean esNorte;

    public Barco(boolean esNorte) {
        this.esNorte = esNorte;
        this.id = barcosContador;
        barcosContador++;
    }

    @Override
    public void run() {
        try {
            System.out.println(
                    "Barco " + id +
                            (esNorte ? " de norte" : " de sur") +
                            " quiere cruzar el canal");
            controlDeAcceso.acquire();
            if (esNorte) {
                while (barcosSur > 0) {
                    controlDeAcceso.release();
                    Thread.sleep(Utils.tiempoAleatorio());
                    controlDeAcceso.acquire();
                }
                barcosNorte++;
            } else {
                while (barcosNorte > 0) {
                    controlDeAcceso.release();
                    Thread.sleep(Utils.tiempoAleatorio());
                    controlDeAcceso.acquire();
                }
                barcosSur++;
            }
            controlDeAcceso.release();

            canal.acquire();
            System.out.println(
                    "Barco " + id +
                            (esNorte ? " de norte" : " de sur") +
                            " cruzando el canal");
            Thread.sleep(Utils.tiempoAleatorio());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                controlDeAcceso.acquire();
                if (esNorte) {
                    barcosNorte--;
                } else {
                    barcosSur--;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(
                        "Barco " + id +
                                (esNorte ? " de norte" : " de sur") +
                                " ha cruzado el canal");
                controlDeAcceso.release();
                canal.release();
            }
        }
    }
}

/**
 * Ejercicio 4
 * Un ascensor en un edificio se mueve en una sola dirección a la vez.
 * Hay personas que quieren subir y otras que quieren bajar.
 * • El ascensor debe mantener una dirección activa mientras haya pasajeros en
 * esa dirección.
 * • Solo puede transportar hasta un número limitado de personas a la vez.
 * • Cuando no hay nadie más esperando, puede cambiar de dirección.
 * Sincronizar direcciones y capacidad en un recurso compartido
 */

class Persona implements Runnable {
    private static final Semaphore controlDeAcceso = new Semaphore(1);
    private static final Semaphore ascensorVacio = new Semaphore(0);

    private static int personasEnAscensor = 0;
    private static boolean subiendo = true;
    private static final int CAPACIDAD_ASCENSOR = 5;
    private static final long TIEMPO_MAXIMO_ESPERA = 3000; // 3 segundos
    private static long ultimaPersonaEntro = 0;
    private static boolean ascensorEnMovimiento = false;

    static int personasContador = 0;
    int id;
    boolean quieroSubir;

    public Persona(boolean quieroSubir) {
        this.quieroSubir = quieroSubir;
        this.id = personasContador;
        personasContador++;
    }

    @Override
    public void run() {
        try {
            System.out.println("Persona " + id + " quiere " + (quieroSubir ? "subir 👆" : "bajar 👇"));
            while (true) {
                controlDeAcceso.acquire();
                if (personasEnAscensor == 0) {
                    subiendo = quieroSubir;
                    personasEnAscensor++;
                    ultimaPersonaEntro = System.currentTimeMillis();
                    System.out.println("Persona " + id + " entra al ascensor (1/" + CAPACIDAD_ASCENSOR + ")");
                    controlDeAcceso.release();
                    break;
                } else if (quieroSubir == subiendo && personasEnAscensor < CAPACIDAD_ASCENSOR
                        && !ascensorEnMovimiento) {
                    personasEnAscensor++;
                    ultimaPersonaEntro = System.currentTimeMillis();
                    System.out.println("Persona " + id + " entra al ascensor (" + personasEnAscensor + "/"
                            + CAPACIDAD_ASCENSOR + ")");

                    if (personasEnAscensor == CAPACIDAD_ASCENSOR) {
                        System.out.println("🔔 ASCENSOR LLENO - Iniciando desplazamiento");
                        ascensorEnMovimiento = true;
                        controlDeAcceso.release();
                        iniciarDesplazamiento();
                        break;
                    }
                    controlDeAcceso.release();
                    break;
                }

                if (personasEnAscensor > 0 && !ascensorEnMovimiento) {
                    long tiempoTranscurrido = System.currentTimeMillis() - ultimaPersonaEntro;
                    if (tiempoTranscurrido > TIEMPO_MAXIMO_ESPERA) {
                        System.out.println("⏱️ TIEMPO MÁXIMO ALCANZADO - Iniciando desplazamiento");
                        ascensorEnMovimiento = true;
                        controlDeAcceso.release();
                        iniciarDesplazamiento();
                        ascensorVacio.acquire();
                        continue;
                    }
                }

                controlDeAcceso.release();
                Thread.sleep(Utils.tiempoAleatorio());
            }

            if (personasEnAscensor > 1 || ascensorEnMovimiento) {
                ascensorVacio.acquire();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void iniciarDesplazamiento() {
        try {
            String direccion = subiendo ? "SUBIENDO ⬆️" : "BAJANDO ⬇️";
            System.out.println("\n🚀 ASCENSOR " + direccion + " CON " + personasEnAscensor + " PERSONAS\n");
            Thread.sleep(2000);

            System.out.println("\n🛑 ASCENSOR LLEGÓ - Bajando pasajeros\n");

            controlDeAcceso.acquire();
            int personasABajar = personasEnAscensor;
            personasEnAscensor = 0;
            ascensorEnMovimiento = false;
            controlDeAcceso.release();

            for (int i = 0; i < personasABajar; i++) {
                ascensorVacio.release();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Ejercicio 5
 * 
 * En una fábrica, varios robots trabajan con una cinta que solo puede usarse
 * por un robot a la vez.
 * • Algunos robots colocan piezas (productores).
 * • Otros robots retiran piezas (consumidores).
 * • La cinta tiene espacio para N piezas máximo.
 */

class Robot implements Runnable {
    private static final Semaphore cinta = new Semaphore(1);
    private static final Semaphore controlDeAcceso = new Semaphore(1);
    private static int piezasEnCinta = 0;
    private static final int capacidadMaxima = 4;

    private static int robotsContador = 0;
    int id;
    int accion;

    public Robot(int accion) {
        this.accion = accion;
        this.id = robotsContador;
        robotsContador++;
    }

    @Override
    public void run() {
        try {
            controlDeAcceso.acquire();
            while (piezasEnCinta + accion > capacidadMaxima || piezasEnCinta + accion < 0) {
                controlDeAcceso.release();
                Thread.sleep(Utils.tiempoAleatorio());
                controlDeAcceso.acquire();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Robot " + id + " quiere " + (accion > 0 ? "colocar" : "retirar") + " piezas");

            try {
                cinta.acquire();
                piezasEnCinta += accion;
                System.out.println("Robot " + id + " " + (accion > 0 ? "colocando" : "retirando") + " piezas");
                System.out.println("Piezas en cinta: " + piezasEnCinta);
                Thread.sleep(Utils.tiempoAleatorio());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println("Robot " + id + " " + (accion > 0 ? "colocó" : "retiró") + " piezas");
                System.out.println();

                cinta.release();
                controlDeAcceso.release();
            }
        }
    }
}

/**
 * Ejercicio 6
 * 
 * Un aeropuerto pequeño tiene una única pista.
 * Los aviones pueden aterrizar o despegar, pero nunca al mismo tiempo.
 * • Si hay aviones aterrizando, los de despegue esperan.
 * • Si no hay nadie aterrizando, la pista puede usarse para despegar.
 * • Los aterrizajes tienen prioridad sobre los despegues.
 * Aplicar prioridad condicional y exclusión mutua entre dos tipos de procesos.
 */

class Avion implements Runnable {
    private static final Semaphore pista = new Semaphore(1);
    private static final Semaphore controlDeAcceso = new Semaphore(1);
    private static List<Avion> aviones = new ArrayList<>();
    private static int avionesContador = 0;
    int id;
    boolean aterriza;

    public Avion(boolean aterriza) {
        this.aterriza = aterriza;
        this.id = avionesContador;
        avionesContador++;
    }

    @Override
    public void run() {
        try {
            controlDeAcceso.acquire();
            if (aterriza) {
                if (aviones.isEmpty()) {
                    aviones.add(this);
                } else {
                    for (int i = 0; i < aviones.size(); i++) {
                        if (!aviones.get(i).aterriza) {
                            aviones.add(i, this);
                            break;
                        }
                    }
                }
            } else {
                aviones.add(this);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            controlDeAcceso.release();
        }

        while (aviones.get(0) != this) {
            try {
                Thread.sleep(Utils.tiempoAleatorio());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        try {
            System.out.println("Avion " + id + " quiere " + (aterriza ? "aterrizar" : "despegar"));
            pista.acquire();
            System.out.println("Avion " + id + " " + (aterriza ? "aterrizando" : "despegando"));
            Thread.sleep(Utils.tiempoAleatorio());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Avion " + id + " " + (aterriza ? "aterrizó" : "despegó"));
            System.out.println();
            try {
                controlDeAcceso.acquire();
                aviones.remove(this);
                controlDeAcceso.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            pista.release();
        }
    }
}

/**
 * Ejercicio 7
 * 
 * Un puente solo puede soportar un peso total limitado.
 * Cada tren de carga tiene un peso distinto.
 * • Los trenes pueden cruzar juntos solo si el peso total no supera el máximo
 * permitido.
 * • Si un tren hace que se exceda el límite, debe esperar.
 * Sincronizar el acceso a un recurso limitado por capacidad cuantitativa, no
 * solo binaria.
 */

class TrenCarga implements Runnable {
    private static final Semaphore controlDeAcceso = new Semaphore(1);
    private static int pesoMaximo = 5000;
    private static int pesoTotal = 0;
    private static int trenesContador = 0;
    int id;
    int peso;

    public TrenCarga() {
        this.peso = Utils.tiempoAleatorio();
        this.id = trenesContador;
        trenesContador++;
    }

    @Override
    public void run() {
        try {
            controlDeAcceso.acquire();
            while (peso + pesoTotal > pesoMaximo) {
                controlDeAcceso.release();
                Thread.sleep(Utils.tiempoAleatorio());
                controlDeAcceso.acquire();
            }
            pesoTotal += peso;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Tren " + id + " (" + peso + "kg) quiere cruzar el puente");
            System.out.println("Peso: " + pesoTotal + " / " + pesoMaximo);
            controlDeAcceso.release();
        }

        try {
            System.out.println("Tren " + id + " cruzando el puente");
            Thread.sleep(Utils.tiempoAleatorio());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {

            try {
                controlDeAcceso.acquire();
                System.out.println("Tren " + id + " (" + peso + "kg) cruzó el puente");
                System.out.println("Peso: " + pesoTotal + " / " + pesoMaximo);
                System.out.println();
                pesoTotal -= peso;
                controlDeAcceso.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

/**
 * Ejercicio 8
 * 
 * Un grupo de ciclistas cruza un puente estrecho.
 * Solo pueden estar 3 ciclistas como máximo y todos deben ir en la misma
 * dirección.
 * • Si llega un ciclista del otro lado, debe esperar hasta que el puente esté
 * libre.
 * • Cuando no queda nadie, se cambia la dirección del paso.
 * Controlar capacidad y dirección compartida usando semáforos.
 */

class Ciclista implements Runnable {
    private static final Semaphore puente = new Semaphore(3);
    private static final Semaphore controlDeAcceso = new Semaphore(1);
    private static int ciclistasEnPuente = 0;
    private static int ciclistasContador = 0;
    int id;
    int direccion;

    public Ciclista(int direccion) {
        this.direccion = direccion;
        this.id = ciclistasContador;
        ciclistasContador++;
    }

    @Override
    public void run() {

        try {
            controlDeAcceso.acquire();
            while (ciclistasEnPuente * direccion < 0 && ciclistasEnPuente * direccion != 0) {
                controlDeAcceso.release();
                Thread.sleep(Utils.tiempoAleatorio());
                controlDeAcceso.acquire();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Ciclista (" + direccion + ") " + id + " quiere cruzar el puente");
            System.out.println("Ciclistas en puente: " + ciclistasEnPuente + " / 3");
            controlDeAcceso.release();
        }

        try {
            puente.acquire();
            ciclistasEnPuente += direccion;
            System.out.println("Ciclista (" + direccion + ") " + id + " cruzando el puente");
            Thread.sleep(Utils.tiempoAleatorio());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                controlDeAcceso.acquire();
                ciclistasEnPuente -= direccion;
                System.out.println("Ciclista (" + direccion + ") " + id + " cruzó el puente");
                System.out.println("Ciclistas en puente: " + ciclistasEnPuente + " / 3");
                System.out.println();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                controlDeAcceso.release();
            }
            puente.release();
        }
    }
}
