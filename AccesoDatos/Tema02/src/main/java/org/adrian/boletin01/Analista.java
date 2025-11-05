package org.adrian.boletin01;

import java.util.ArrayList;
import java.util.List;

public class Analista {

    BaseDeDatos baseDeDatos;

    List<Etapa> etapas;
    List<Ciclista> ciclistas;
    List<Equipo> equipos;
    List<Carrera> carreras;
    List<ResultadosPuerto> resultadosPuerto;
    List<ResultadosSprint> resultadosSprint;
    List<ResultadosEtapa> resultadosEtapa;
    List<PuntosMeta> puntosMeta;
    List<Puerto> puertos;
    List<Sprints> sprints;

    public Analista() throws Exception {
        this.baseDeDatos = new BaseDeDatos();
        try {
            this.baseDeDatos.conectar();
            this.cargarDatos();
            this.baseDeDatos.desconectar();
        } catch (Exception e) {
            throw new Exception("Error al cargar datos: " + e.getMessage());
        }
    }

    public void cargarDatos() throws Exception {
        try {
            this.etapas = new ArrayList<>();
            this.ciclistas = new ArrayList<>();
            this.equipos = new ArrayList<>();
            this.carreras = new ArrayList<>();
            this.resultadosPuerto = new ArrayList<>();
            this.resultadosSprint = new ArrayList<>();
            this.resultadosEtapa = new ArrayList<>();
            this.puntosMeta = new ArrayList<>();
            this.puertos = new ArrayList<>();
            this.sprints = new ArrayList<>();

            this.etapas = this.baseDeDatos.listarEtapas();
            this.ciclistas = this.baseDeDatos.listarCiclistasPorEquipo(1);
            this.equipos = this.baseDeDatos.listarEquipos();
            this.carreras = this.baseDeDatos.listarCarreras();
            this.resultadosPuerto = this.baseDeDatos.listarResultadosPuerto();
            this.resultadosSprint = this.baseDeDatos.listarResultadosSprint();
            this.resultadosEtapa = this.baseDeDatos.listarResultadosEtapa();
            this.puntosMeta = this.baseDeDatos.listarPuntosMeta();
            this.puertos = this.baseDeDatos.listarPuertos();
            this.sprints = this.baseDeDatos.listarSprints();
            this.baseDeDatos.desconectar();
        } catch (Exception e) {
            throw new Exception("Error al cargar datos: " + e.getMessage());
        }
    }

    public List<Equipo> obtenerEquipos() {
        return this.equipos;
    }

    public List<Ciclista> obtenerCiclistasPorEquipo(int id_equipo) {
        return this.ciclistas.stream().filter(ciclista -> ciclista.getId_equipo() == id_equipo).toList();
    }
}
