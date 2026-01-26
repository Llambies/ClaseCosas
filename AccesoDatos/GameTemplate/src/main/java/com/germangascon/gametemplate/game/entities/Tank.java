package com.germangascon.gametemplate.game.entities;

import com.germangascon.gametemplate.core.CollisionLayer;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.entities.WaypointEntity;
import com.germangascon.gametemplate.math.Vector2;
import com.germangascon.gametemplate.game.Economy;
import com.germangascon.gametemplate.game.EnemyType;
import com.germangascon.gametemplate.game.EnemyLevel;

import java.util.List;

/**
 * <p>
 * <strong>Tanque</strong>
 * </p>
 * <p>
 * Descripción
 * </p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-12<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class Tank extends WaypointEntity {
    private final EnemyType enemyType;
    private final EnemyLevel enemyLevel;
    private final int reward;

    public Tank(float x, float y, int hp, int damage, float velocity, List<Vector2> waypoints) {
        this(x, y, EnemyType.BASIC, EnemyLevel.LEVEL_1, waypoints);
    }

    public Tank(float x, float y, EnemyType enemyType, EnemyLevel enemyLevel, List<Vector2> waypoints) {
        // Llamar a super primero con estadísticas calculadas inline
        super(x, y, 64, 64, 38, 30, 
              enemyType.calculateStats(enemyLevel.getLevel())[0],  // hp
              enemyType.calculateStats(enemyLevel.getLevel())[1],  // damage
              enemyType.getSpritePath(), 
              enemyType.calculateStats(enemyLevel.getLevel())[2],  // speed
              waypoints);
        
        // Asignar campos después de super
        this.enemyType = enemyType;
        this.enemyLevel = enemyLevel;
        int[] stats = enemyType.calculateStats(enemyLevel.getLevel());
        this.reward = stats[3];
        
        // Está en la capa ENEMY
        setCollisionLayer(CollisionLayer.LAYER_ENEMY);
        // Y quiere colisionar con la capa PLAYER
        addCollisionMask(CollisionLayer.LAYER_PLAYER);
        addCollisionMask(CollisionLayer.LAYER_ENEMY);
        // si quiéramos que colisione con más capas las iríamos añadiendo
        // addCollisionMask(CollisionLayer.LAYER_PLAYER_BULLET);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void preUpdate(double deltaTime) {

    }

    @Override
    public void postUpdate(double deltaTime) {

    }

    @Override
    public void lateUpdate(double deltaTime) {

    }

    @Override
    public void onCollision(Entity entity) {

    }

    @Override
    public void destroy() {
        super.destroy();
        Economy.getInstance().ganarDinero(reward);
        
        // Notificar al WaveManager que un enemigo ha sido destruido
        if (gameContext != null) {
            com.germangascon.gametemplate.game.WaveManager waveManager = gameContext.getWaveManager();
            if (waveManager != null) {
                waveManager.onEnemyDestroyed();
            }
        }
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public EnemyLevel getEnemyLevel() {
        return enemyLevel;
    }

    public int getReward() {
        return reward;
    }
}
