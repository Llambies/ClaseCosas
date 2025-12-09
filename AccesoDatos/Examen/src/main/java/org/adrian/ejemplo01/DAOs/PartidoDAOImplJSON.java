package org.adrian.ejemplo01.DAOs;

import java.io.IOException;
import java.lang.reflect.Type;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.adrian.ejemplo01.model.Equipo;

import org.adrian.ejemplo01.model.Partido;
import org.json.JSONArray;
import org.json.JSONObject;

public class PartidoDAOImplJSON implements PartidoDAO {

    private final List<Partido> partidosCache;
    private final EquipoDAO equipoDAO;
    private final String filePath = "src/main/resources/resultados.json";

    public PartidoDAOImplJSON(EquipoDAO equipoDAO) {
        this.equipoDAO = equipoDAO;
        this.partidosCache = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        try {
            String content = Files.readString(Path.of(filePath));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                
                // Asumimos estructura del JSON basada en los campos
                int id = obj.getInt("id");
                int jornada = obj.getInt("numero_jornada");
                int localId = obj.getInt("id_equipo_local"); // Nombre supuesto campo ID
                int visitId = obj.getInt("id_equipo_visitante"); // Nombre supuesto campo ID
                int gl = obj.getInt("goles_equipo_local");
                int gv = obj.getInt("goles_equipo_visitante");

                // Usamos el DAO inyectado para buscar los objetos Equipo
                Optional<Equipo> local = equipoDAO.findById(localId);
                Optional<Equipo> visitante = equipoDAO.findById(visitId);

                if (local.isPresent() && visitante.isPresent()) {
                    partidosCache.add(new Partido(id, jornada, local.get(), visitante.get(), gl, gv));
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo JSON: " + e.getMessage());
        }
    }

    @Override
    public List<Partido> findAll() {
        return new ArrayList<>(partidosCache);
    }

    @Override
    public Optional<Partido> findById(int id) {
        return partidosCache.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }

    @Override
    public List<Partido> findByJornada(int numeroJornada) {
        return partidosCache.stream()
                .filter(p -> p.getNumeroJornada() == numeroJornada)
                .collect(Collectors.toList());
    }
}