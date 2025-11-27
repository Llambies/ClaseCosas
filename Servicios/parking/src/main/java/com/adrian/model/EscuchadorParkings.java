package com.adrian.model;

import com.adrian.model.Coche;

import java.util.List;

public interface EscuchadorParkings {
    void cambioRecaudacion(double value);

    void acaboUnCoche(int acabados, int totales);

    void cambioPlazas(int ocupadas, int totales);

    void cambioPlazasElectricas(int ocupadas, int totales);

    void cambioPlazasEspeciales(int ocupadas, int totales);

    void cambioCochesEntrando(List<Coche> coches);

    void cambioCochesSaliendo(List<Coche> coches);

}
