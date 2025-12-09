package org.adrian.ejemplo01.DAOs;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.adrian.ejemplo01.model.Equipo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EquipoDAOImplJSON implements EquipoDAO {
    Gson gson = new Gson();
    String path;

    public EquipoDAOImplJSON(String path) {
        this.path = path;
    }

    public List<Equipo> findAll() {
        List<Equipo> equipos = new ArrayList<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get(path)));
            Type type = new TypeToken<List<Equipo>>() {
            }.getType();
            equipos = gson.fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return equipos;
    }

    @Override
    public Optional<Equipo> findById(int id) {
        Optional<Equipo> equipo = Optional.empty();
        List<Equipo> equipos = findAll();
        for (Equipo e : equipos) {
            if (e.getId() == id) {
                equipo = Optional.of(e);
                break;
            }
        }
        return equipo;
    }

    @Override
    public List<Equipo> findByNombre(String nombre) {
        List<Equipo> equipos = findAll();
        List<Equipo> result = new ArrayList<>();
        for (Equipo e : equipos) {
            if (e.getNombre().equals(nombre)) {
                result.add(e);
            }
        }
        return result;
    }
}
