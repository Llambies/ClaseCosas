package com.adrian.model;

import javafx.application.Platform;

public class Coche implements Runnable {

    static int contador;
    String id;
    Parking parking;

    boolean esElectrico;
    boolean esEspecial;

    public Coche(Parking parking, boolean esElectrico, boolean esEspecial) {
        this.id = "C" + String.format("%03d", contador);
        this.parking = parking;
        this.esElectrico = esElectrico;
        this.esEspecial = esEspecial;

        contador++;
    }

    @Override
    public void run() {
        try {

            parking.escritor.acquire();
            parking.eventos.add(this.id + " Entra a la cola");
            parking.escritor.release();

            parking.entradas.acquire();
            parking.cochesEntrando.add(this);
            parking.escritor.acquire();
            parking.eventos.add(this.id + " esta en la entrada");
            parking.escritor.release();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    parking.escuchador.cambioCochesEntrando(parking.cochesEntrando);
                }
            });
            Thread.sleep(Math.round(Math.random() * 1000));

            if (esEspecial) {
                parking.plazasEspeciales.acquire();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        parking.escuchador.cambioPlazasEspeciales(parking.plazasEspeciales.availablePermits(),
                                parking.totalPlazasEspeciales);
                    }
                });
            } else if (esElectrico) {
                parking.plazasElectricas.acquire();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        parking.escuchador.cambioPlazasElectricas(parking.plazasElectricas.availablePermits(),
                                parking.totalPlazasElectricas);
                    }
                });
            } else {
                parking.plazas.acquire();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        parking.escuchador.cambioPlazas(parking.plazas.availablePermits(),
                                parking.totalPlazas);
                    }
                });
            }
            parking.cochesEntrando.remove(this);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    parking.escuchador.cambioCochesEntrando(parking.cochesEntrando);
                }
            });
            parking.entradas.release();

            parking.escritor.acquire();
            parking.eventos.add(this.id + " aparco");
            parking.escritor.release();

            long tiempoEnElParking = Math.round(Math.random() * 100000);
            Thread.sleep(tiempoEnElParking);

            parking.salidas.acquire();
            parking.cochesSaliendo.add(this);
            parking.escritor.acquire();
            parking.eventos.add(this.id + " esta saliendo");
            parking.escritor.release();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    parking.escuchador.cambioCochesSaliendo(parking.cochesSaliendo);
                }
            });
            Thread.sleep(Math.round(Math.random() * 1000));

            if (esEspecial) {
                parking.plazasEspeciales.release();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        parking.escuchador.cambioPlazasEspeciales(parking.plazasEspeciales.availablePermits(),
                                parking.totalPlazasEspeciales);
                    }
                });
            } else if (esElectrico) {
                parking.plazasElectricas.release();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        parking.escuchador.cambioPlazasElectricas(parking.plazasElectricas.availablePermits(),
                                parking.totalPlazasElectricas);
                    }
                });
            } else {
                parking.plazas.release();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        parking.escuchador.cambioPlazas(parking.plazas.availablePermits(),
                                parking.totalPlazas);
                    }
                });
            }

            parking.recaudador.acquire();
            Thread.sleep(Math.round(Math.random() * 100));
            parking.pagar(this, tiempoEnElParking);
            parking.recaudador.release();

            parking.escritor.acquire();
            parking.eventos.add(this.id + " pago");
            parking.escritor.release();

            parking.cochesSaliendo.remove(this);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    parking.escuchador.cambioCochesSaliendo(parking.cochesSaliendo);
                }
            });
            parking.cochesCompletados++;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    parking.escuchador.acaboUnCoche(parking.cochesCompletados, parking.coches);
                }
            });
            parking.salidas.release();
            parking.escritor.acquire();
            parking.eventos.add(this.id + " salio");
            parking.escritor.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Coche: " + id;
    }

    public String getId() {
        return id;
    }

}
