package org.adrian.ejemplo01.repository;

import java.util.List;

import org.adrian.ejemplo01.TablaAscii;
import org.adrian.ejemplo01.DAOs.EquipoDAO;
import org.adrian.ejemplo01.DAOs.PartidoDAO;
import org.adrian.ejemplo01.model.*;// Importamos la clase de utilidad

import java.util.List;

public class LigaRepository {
    private final EquipoDAO equipoDAO;
    private final PartidoDAO partidoDAO;

    public LigaRepository(EquipoDAO equipoDAO, PartidoDAO partidoDAO) {
        this.equipoDAO = equipoDAO;
        this.partidoDAO = partidoDAO;
    }

    public String getEquipos() {
        // 1. Instanciamos la tabla con las cabeceras
        TablaAscii tabla = new TablaAscii("Id", "Equipo", "Fundación");

        List<Equipo> equipos = equipoDAO.findAll();

        // 2. Rellenamos filas
        for (Equipo e : equipos) {
            tabla.agregarFila(
                    String.valueOf(e.getId()),
                    e.getNombre(),
                    String.valueOf(e.getAnyoFundacion()));
        }

        // 3. Renderizamos
        return tabla.renderizar();
    }

    public String getResultados() {
        StringBuilder sb = new StringBuilder();

        // Iteramos las 13 jornadas
        for (int i = 1; i <= 13; i++) {
            sb.append("Jornada ").append(i).append("\n");

            // Creamos una tabla nueva para cada jornada
            TablaAscii tabla = new TablaAscii("Equipo Local", "GL", "GV", "Equipo Visitante");

            List<Partido> partidos = partidoDAO.findByJornada(i);
            for (Partido p : partidos) {
                tabla.agregarFila(
                        p.getEquipoLocal().getNombre(),
                        String.valueOf(p.getGolesEquipoLocal()),
                        String.valueOf(p.getGolesEquipoVisitante()),
                        p.getEquipoVisitante().getNombre());
            }

            sb.append(tabla.renderizar());
            sb.append("\n"); // Espacio entre jornadas
        }
        return sb.toString();
    }

}