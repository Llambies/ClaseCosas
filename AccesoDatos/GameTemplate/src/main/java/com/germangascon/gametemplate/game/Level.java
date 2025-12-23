package com.germangascon.gametemplate.game;

import com.germangascon.gametemplate.math.GridPos;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Modelo de datos para representar un nivel del juego
 */
public class Level {
    @BsonId
    private ObjectId id;
    private String name;
    private Map<String, TilePosition> tiles;
    private List<WaypointPosition> waypoints;

    public Level() {
        this.tiles = new HashMap<>();
        this.waypoints = new ArrayList<>();
    }

    public Level(String name) {
        this();
        this.name = name;
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, TilePosition> getTiles() {
        return tiles;
    }

    public void setTiles(Map<String, TilePosition> tiles) {
        this.tiles = tiles;
    }

    public List<WaypointPosition> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<WaypointPosition> waypoints) {
        this.waypoints = waypoints;
    }

    /**
     * Clase interna para representar la posición de un tile
     */
    public static class TilePosition {
        private int x;
        private int y;
        private String spritePath;

        public TilePosition() {}

        public TilePosition(int x, int y, String spritePath) {
            this.x = x;
            this.y = y;
            this.spritePath = spritePath;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String getSpritePath() {
            return spritePath;
        }

        public void setSpritePath(String spritePath) {
            this.spritePath = spritePath;
        }
    }

    /**
     * Clase interna para representar la posición de un waypoint
     */
    public static class WaypointPosition {
        private int x;
        private int y;

        public WaypointPosition() {}

        public WaypointPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}