package org.adrian.ejemplo01.model;

public class Partido {
    private final int id;
    private final int numero_jornada;
    private final Equipo equipoLocal;
    private final Equipo equipoVisitante;
    private final int goles_equipo_local;
    private final int goles_equipo_visitante;

    public Partido(int id, int numero_jornada, Equipo equipoLocal, Equipo equipoVisitante, int golesEquipoLocal,
            int golesEquipoVisitante) {
        this.id = id;
        this.numero_jornada = numero_jornada;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.goles_equipo_local = golesEquipoLocal;
        this.goles_equipo_visitante = golesEquipoVisitante;
    }

    public int getId() {
        return id;
    }

    public int getNumeroJornada() {
        return numero_jornada;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public int getGolesEquipoLocal() {
        return goles_equipo_local;
    }

    public int getGolesEquipoVisitante() {
        return goles_equipo_visitante;
    }

}
