package org.adrian.ejemplo01.mongodb;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoPrueba {
    public static void main(String[] args) {
        final String user = "root";
        final String pass = "test";
        final String host = "localhost";
        final String port = "27017";
        String url = "mongodb://" + user + ":" + pass + "@" + host + ":" + port + "/?authSource=admin";

        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase db = mongoClient.getDatabase("ciclistas");
            MongoCollection<Document> equipos = db.getCollection("equipo");

            for (Document equipo : equipos.find()) {
                System.out.println(equipo);
            }

            Document d = new Document("nombre", "Equipazo").append("zona", "Sur");
            equipos.insertOne(d);

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
