package com.llambies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ParkinAPI {
    private final String baseUrl;
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public ParkinAPI(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<Plaza> listarPlazas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/plazas"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("GET /plazas -> " + response.statusCode() + " | " + response.body());
        }
        return mapper.readValue(response.body(), new TypeReference<List<Plaza>>() {});
    }

    public Plaza insertarPlaza(Plaza p) throws Exception {
        String json = mapper.writeValueAsString(java.util.Map.of(
                "numero", p.getNumero(),
                "planta", p.getPlanta(),
                "tipo", p.getTipo() != null ? p.getTipo() : ""
        ));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/plazas"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("POST /plazas -> " + response.statusCode() + " | " + response.body());
        }
        return mapper.readValue(response.body(), Plaza.class);
    }

    public void actualizarPlaza(Plaza p) throws Exception {
        if (p.getId() == null || p.getId().isEmpty()) {
            throw new RuntimeException("Se necesita _id para actualizar");
        }
        String json = mapper.writeValueAsString(java.util.Map.of(
                "numero", p.getNumero(),
                "planta", p.getPlanta(),
                "tipo", p.getTipo() != null ? p.getTipo() : ""
        ));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/plazas/" + p.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("PUT /plazas/:id -> " + response.statusCode() + " | " + response.body());
        }
    }

    public void borrarPlaza(String id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/plazas/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("DELETE /plazas/:id -> " + response.statusCode() + " | " + response.body());
        }
    }

    public boolean verificarPinAdmin(String pin) throws Exception {
        String json = mapper.writeValueAsString(java.util.Map.of("pin", pin != null ? pin : ""));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/auth/admin"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            var node = mapper.readTree(response.body());
            return node.has("ok") && node.get("ok").asBoolean();
        }
        return false;
    }

    public int plazasDisponibles(String tipo) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/plazas/disponibles?tipo=" + java.net.URLEncoder.encode(tipo != null ? tipo : "", "UTF-8")))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException(response.body());
        }
        var node = mapper.readTree(response.body());
        return node.has("count") ? node.get("count").asInt() : 0;
    }

    public java.util.Map<String, Object> iniciarEstacionamiento(String tipoVehiculo) throws Exception {
        String json = mapper.writeValueAsString(java.util.Map.of("tipoVehiculo", tipoVehiculo != null ? tipoVehiculo : ""));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/estacionamientos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 409) {
            throw new RuntimeException("No hay plazas disponibles para " + tipoVehiculo);
        }
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException(response.body());
        }
        return mapper.readValue(response.body(), new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, Object>>() {});
    }

    public java.util.Map<String, Object> obtenerEstacionamientoActivo(String id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/estacionamientos/" + id))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException(response.body());
        }
        return mapper.readValue(response.body(), new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, Object>>() {});
    }

    public java.util.Map<String, Object> finalizarEstacionamiento(String id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/estacionamientos/" + id + "/salir"))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException(response.body());
        }
        return mapper.readValue(response.body(), new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, Object>>() {});
    }

    public java.util.Map<String, Double> obtenerTarifas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/tarifas"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException(response.body());
        }
        return mapper.readValue(response.body(), new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, Double>>() {});
    }
}
