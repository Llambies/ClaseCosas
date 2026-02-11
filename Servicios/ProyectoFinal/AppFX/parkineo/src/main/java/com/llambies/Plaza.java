package com.llambies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Plaza {
    private String id;  // MongoDB _id
    private int numero;
    private int planta;
    private String tipo;

    public Plaza() {}

    public Plaza(int numero, int planta, String tipo) {
        this.numero = numero;
        this.planta = planta;
        this.tipo = tipo;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(Object id) {
        if (id == null) {
            this.id = null;
            return;
        }
        if (id instanceof String) {
            this.id = (String) id;
            return;
        }
        if (id instanceof java.util.Map) {
            Object oid = ((java.util.Map<?, ?>) id).get("$oid");
            this.id = oid != null ? oid.toString() : null;
            return;
        }
        this.id = id.toString();
    }

    public int getNumero() {
        return numero;
    }

    public int getPlanta() {
        return planta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
