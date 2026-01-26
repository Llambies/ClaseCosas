package com.germangascon.gametemplate.game;

import com.germangascon.gametemplate.core.GameScene;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.game.entities.*;
import com.germangascon.gametemplate.game.TowerType;
import com.germangascon.gametemplate.game.WaveManager;
import com.germangascon.gametemplate.game.EnemyType;
import com.germangascon.gametemplate.game.EnemyLevel;
import com.germangascon.gametemplate.math.Vector2;
import com.germangascon.gametemplate.math.GridPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * <strong>EntityFactory</strong>
 * </p>
 * <p>
 * Descripción
 * </p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-15<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class EntityFactory {
    private final GameScene gameScene;
    private final HashMap<Class<?>, List<Vector2>> waypointsMap;

    public EntityFactory(GameScene gameScene, List<GridPos> waypoints) {
        waypointsMap = new HashMap<>();
        generateWaypoints(waypoints);
        this.gameScene = gameScene;
    }

    private void generateWaypoints(List<GridPos> waypoints) {
        List<Vector2> waypointsList = new ArrayList<>();
        for (GridPos waypoint : waypoints) {
            waypointsList.add(waypoint.getCentro());
        }
        waypointsMap.put(Tank.class, waypointsList);
    }

    public Tank spawnTank(float x, float y, int level) {
        // Obtener información de la oleada actual para determinar tipo y nivel
        WaveManager waveManager = gameScene.getWaveManager();
        int currentWave = waveManager != null ? waveManager.getCurrentWave() : 1;
        
        // Determinar tipo de enemigo basado en la oleada
        EnemyType enemyType = determineEnemyType(currentWave);
        
        // Determinar nivel de enemigo basado en la oleada
        EnemyLevel enemyLevel = EnemyLevel.fromWave(currentWave);
        
        Tank tank = new Tank(x, y, enemyType, enemyLevel, waypointsMap.get(Tank.class));
        gameScene.spawn(tank);
        
        // Notificar al WaveManager que se ha spawneado un enemigo
        if (waveManager != null) {
            waveManager.onEnemySpawned();
        }
        
        return tank;
    }

    /**
     * Determina el tipo de enemigo basado en la oleada actual
     * @param wave Número de oleada
     * @return Tipo de enemigo
     */
    private EnemyType determineEnemyType(int wave) {
        // Variar tipos de enemigos según la oleada
        if (wave <= 2) {
            return EnemyType.BASIC;
        } else if (wave <= 5) {
            // Mezclar básicos y rápidos
            return (wave % 2 == 0) ? EnemyType.BASIC : EnemyType.FAST;
        } else if (wave <= 10) {
            // Mezclar todos los tipos
            int typeIndex = wave % 3;
            if (typeIndex == 0) return EnemyType.BASIC;
            if (typeIndex == 1) return EnemyType.FAST;
            return EnemyType.HEAVY;
        } else {
            // Oleadas avanzadas: más variedad
            int typeIndex = wave % 4;
            switch (typeIndex) {
                case 0: return EnemyType.BASIC;
                case 1: return EnemyType.FAST;
                case 2: return EnemyType.HEAVY;
                default: return EnemyType.ARMORED;
            }
        }
    }

    public Tower spawnTower(float x, float y, int level) {
        // Usar tipo básico por defecto para compatibilidad
        return spawnTower(x, y, TowerType.BASIC, level);
    }

    public Tower spawnTower(float x, float y, TowerType towerType, int level) {
        Tower tower = new Tower(x, y, towerType, level);
        gameScene.spawn(tower);
        return tower;
    }

    public Bullet spawnBullet(Entity owner, float x, float y, Vector2 target, int level) {
        // TODO: ajustar propiedades según el nivel
        Bullet bullet = new Bullet(owner, x, y, 1, target);
        gameScene.spawn(bullet);
        return bullet;
    }

    public AIBullet spawnAIBullet(Entity owner, float x, float y, Vector2 target, int level) {
        // TODO: ajustar propiedades según el nivel
        AIBullet aiBullet = new AIBullet(owner, x, y, 1, target);
        gameScene.spawn(aiBullet);
        return aiBullet;
    }

    public <T extends Entity> Spawner spawnSpawner(Class<T> entityClass, float x, float y, float cooldown, int level) {
        if (entityClass == Tank.class) {
            Spawner spawner = new Spawner(Tank.class, x, y, 1, 0, cooldown, level);
            gameScene.spawn(spawner);
            return spawner;
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
