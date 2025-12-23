package com.germangascon.gametemplate.game.entities;

import com.germangascon.gametemplate.core.CollisionLayer;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.entities.WaypointEntity;
import com.germangascon.gametemplate.math.Vector2;
import com.germangascon.gametemplate.game.Economy;

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

    public Tank(float x, float y, int hp, int damage, float velocity, List<Vector2> waypoints) {
        super(x, y, 64, 64, 38, 30, hp, damage, "/tilesheet/tank", velocity, waypoints);
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
        Economy.getInstance().ganarDinero(10);
    }
}
