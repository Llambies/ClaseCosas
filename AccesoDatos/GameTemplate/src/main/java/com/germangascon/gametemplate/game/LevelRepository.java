package com.germangascon.gametemplate.game;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar niveles en MongoDB
 */
public class LevelRepository {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "gametemplate";
    private static final String COLLECTION_NAME = "levels";
    
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Level> collection;

    public LevelRepository() {
        // Configurar codec para POJOs
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );
    
        // Conectar a MongoDB con el codec registry configurado
        MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(CONNECTION_STRING))
            .codecRegistry(pojoCodecRegistry)
            .build();
        
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME, Level.class);
    }

    /**
     * Guarda un nivel en MongoDB
     */
    public void saveLevel(Level level) {
        if (level.getId() == null) {
            collection.insertOne(level);
        } else {
            collection.replaceOne(Filters.eq("_id", level.getId()), level);
        }
    }

    /**
     * Busca un nivel por nombre
     */
    public Level findByName(String name) {
        return collection.find(Filters.eq("name", name)).first();
    }

    /**
     * Busca un nivel por ID
     */
    public Level findById(ObjectId id) {
        return collection.find(Filters.eq("_id", id)).first();
    }

    /**
     * Obtiene todos los niveles
     */
    public List<Level> findAll() {
        List<Level> levels = new ArrayList<>();
        collection.find().into(levels);
        return levels;
    }

    /**
     * Elimina un nivel por ID
     */
    public void deleteById(ObjectId id) {
        collection.deleteOne(Filters.eq("_id", id));
    }

    /**
     * Elimina un nivel por nombre
     */
    public void deleteByName(String name) {
        collection.deleteOne(Filters.eq("name", name));
    }

    /**
     * Cierra la conexión con MongoDB
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}