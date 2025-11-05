package org.adrian;

public class Fan implements Runnable {
    private static int contadorFanes = 0;
    private int id;
    private Concierto concierto;

    public Fan(Concierto concierto) {
        this.id = ++contadorFanes;
        this.concierto = concierto;
    }

    @Override
    public void run() {
        try {

            concierto.getControlDeAcceso().acquire();
            concierto.getFansEsperando().add(this);
            System.out.println("Fan " + id + " esta esperando en la fila con turno: "
                    + concierto.getFansEsperando().indexOf(this));
            concierto.getControlDeAcceso().release();

            concierto.getControlDeAcceso().acquire();
            while (concierto.getFansEsperando().get(0) != this) {
                concierto.getControlDeAcceso().release();
                Thread.sleep(1000);
                concierto.getControlDeAcceso().acquire();
            }
            concierto.getControlDeAcceso().release();

            concierto.getTaquillas().acquire();
            concierto.getControlDeAcceso().acquire();
            concierto.getFansEsperando().remove(this);
            concierto.getFansEnTaquilla().add(this);
            System.out.println("Fan " + id + " esta en la taquilla");
            concierto.getControlDeAcceso().release();

            try {
                concierto.getEntradas().acquire();
                System.out.println("Fan " + id + " ha comprado una entrada");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
            concierto.getTaquillas().release();
            concierto.getControlDeAcceso().acquire();
            concierto.getFansEnTaquilla().remove(this);
            System.out.println("Fan " + id + " ha salido de la taquilla");
            // System.out.println("Fans esperando: " + concierto.getFansEsperando().size());
            // System.out.println("Fans en taquilla: " + concierto.getFansEnTaquilla().size());
            concierto.getControlDeAcceso().release();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public Concierto getConcierto() {
        return concierto;
    }
}
