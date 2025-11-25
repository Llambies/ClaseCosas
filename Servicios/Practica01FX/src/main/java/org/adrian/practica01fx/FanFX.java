package org.adrian.practica01fx;

import javafx.application.Platform;

public class FanFX implements Runnable {

    private Concierto concierto;
    private ConciertoFX conciertoFX;
    private int id;

    public FanFX(Concierto concierto, ConciertoFX conciertoFX, int id) {
        this.concierto = concierto;
        this.conciertoFX = conciertoFX;
        this.id = id;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(1000);
            boolean compraExitosa = concierto.venderEntrada();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    conciertoFX.fanTerminado(id, compraExitosa);
                }
            });
        } catch (Exception e) {
        }

    }
}
