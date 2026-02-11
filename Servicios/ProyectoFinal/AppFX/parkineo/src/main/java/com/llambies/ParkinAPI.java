package com.llambies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ParkinAPI {
    private final String baseUrl; // Ej: "http://localhost:3000"
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
            throw new RuntimeException("GET /plazas -> " +
                    response.statusCode() + " | " + response.body());
        }
        return mapper.readValue(response.body(),
                new TypeReference<List<Plaza>>() {});
    }

    public void insertarPlaza(Plaza p) throws Exception {
        String json = mapper.writeValueAsString(p);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/insertPlaza"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("POST /insertPlaza -> " +
                    response.statusCode() + " | " + response.body());
        }
    }

    public void actualizarPlaza(Plaza p) throws Exception {
        String json = mapper.writeValueAsString(p);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/updatePlaza"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("PUT /update -> " +
                    response.statusCode() + " | " + response.body());
        }
    }

    // Opción A (recomendada): DELETE /delete/:codigo
    public void borrarPlazaPorPath(String numero) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/delete/" + numero))
                .DELETE()
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("DELETE /delete/:codigo -> " +
                    response.statusCode() + " | " + response.body());
        }
    }

    // Opción B: DELETE /delete con body JSON {codigo:"..."}
    public void borrarDeportistaPorBody(String codigo) throws Exception {
        String json = "{\"codigo\":\"" + codigo + "\"}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/delete"))
                .header("Content-Type", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("DELETE /delete (body) -> " +
                    response.statusCode() + " | " + response.body());
        }

    }
}