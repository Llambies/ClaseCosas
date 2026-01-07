package com.germangascon.gametemplate.game;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de datos para representar un estado guardado de partida
 */
public class GameSave {
    @BsonId
    private ObjectId id;
    private String levelName;
    private int currentWave;
    private int money;
    private List<TowerSave> towers;

    public GameSave() {
        this.towers = new ArrayList<>();
    }

    public GameSave(String levelName, int currentWave, int money) {
        this();
        this.levelName = levelName;
        this.currentWave = currentWave;
        this.money = money;
    }

    // Getters y Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public void setCurrentWave(int currentWave) {
        this.currentWave = currentWave;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<TowerSave> getTowers() {
        return towers;
    }

    public void setTowers(List<TowerSave> towers) {
        this.towers = towers;
    }

    /**
     * Clase interna para representar una torreta guardada
     */
    public static class TowerSave {
        private float x;
        private float y;
        private String towerType; // Nombre del TowerType (BASIC, RAPID, HEAVY)
        private int level;

        public TowerSave() {}

        public TowerSave(float x, float y, String towerType, int level) {
            this.x = x;
            this.y = y;
            this.towerType = towerType;
            this.level = level;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public String getTowerType() {
            return towerType;
        }

        public void setTowerType(String towerType) {
            this.towerType = towerType;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}

