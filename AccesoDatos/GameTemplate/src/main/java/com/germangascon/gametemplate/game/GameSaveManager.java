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
 * Gestor para guardar y cargar partidas en MongoDB
 */
public class GameSaveManager {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "gametemplate";
    private static final String COLLECTION_NAME = "game_saves";
    
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<GameSave> collection;

    public GameSaveManager() {
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
        collection = database.getCollection(COLLECTION_NAME, GameSave.class);
    }

    /**
     * Guarda una partida en MongoDB
     */
    public void saveGame(GameSave gameSave) {
        if (gameSave.getId() == null) {
            collection.insertOne(gameSave);
        } else {
            collection.replaceOne(Filters.eq("_id", gameSave.getId()), gameSave);
        }
    }

    /**
     * Busca una partida guardada por nombre de nivel
     * Retorna la más reciente si hay múltiples
     */
    public GameSave findByLevelName(String levelName) {
        return collection.find(Filters.eq("levelName", levelName))
                .sort(com.mongodb.client.model.Sorts.descending("_id"))
                .first();
    }

    /**
     * Busca una partida guardada por ID
     */
    public GameSave findById(ObjectId id) {
        return collection.find(Filters.eq("_id", id)).first();
    }

    /**
     * Obtiene todas las partidas guardadas
     */
    public List<GameSave> findAll() {
        List<GameSave> saves = new ArrayList<>();
        collection.find().into(saves);
        return saves;
    }

    /**
     * Obtiene todas las partidas guardadas para un nivel específico
     */
    public List<GameSave> findAllByLevelName(String levelName) {
        List<GameSave> saves = new ArrayList<>();
        collection.find(Filters.eq("levelName", levelName)).into(saves);
        return saves;
    }

    /**
     * Elimina una partida guardada por ID
     */
    public void deleteById(ObjectId id) {
        collection.deleteOne(Filters.eq("_id", id));
    }

    /**
     * Elimina todas las partidas guardadas para un nivel específico
     */
    public void deleteByLevelName(String levelName) {
        collection.deleteMany(Filters.eq("levelName", levelName));
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

