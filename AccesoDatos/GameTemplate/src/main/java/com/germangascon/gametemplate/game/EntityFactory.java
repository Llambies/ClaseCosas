package com.germangascon.gametemplate.game;

import com.germangascon.gametemplate.core.GameScene;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.game.entities.*;
import com.germangascon.gametemplate.game.TowerType;
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
        // TODO: ajustar propiedades según el nivel
        Tank tank = new Tank(x, y, 5, 1, 170, waypointsMap.get(Tank.class));
        gameScene.spawn(tank);
        return tank;
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
