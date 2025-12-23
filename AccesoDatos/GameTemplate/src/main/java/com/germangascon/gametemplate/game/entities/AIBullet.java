package com.germangascon.gametemplate.game.entities;

import com.germangascon.gametemplate.core.CollisionLayer;
import com.germangascon.gametemplate.entities.AIDynamicEntity;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.math.Vector2;

/**
 * <p><strong>AIBullet</strong></p>
 * <p>Descripción</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-12<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class AIBullet extends AIDynamicEntity {
    private final Entity owner;
    private double ignoreOwnerTime;
    // Los proyectiles "inteligentes" tienen un tiempo de vida limitado (Time to live)
    private double ttl;

    public AIBullet(Entity owner, float x, float y, int damage, Vector2 target) {
        super(x, y, 64, 64, 8, 8, 1, damage, "/tilesheet/bullet", target, 350);
        this.owner = owner;
        this.ignoreOwnerTime = 0.5;
        this.ttl = 7;
        // Está en la capa ENEMY_BULLET
        setCollisionLayer(CollisionLayer.LAYER_ENEMY_BULLET);
        // Y quiere colisionar con la capa PLAYER
        addCollisionMask(CollisionLayer.LAYER_PLAYER);
        addCollisionMask(CollisionLayer.LAYER_ENEMY);
        // si queremos que colisione con más capas, las iremos añadiendo
        // addCollisionMask(CollisionLayer.LAYER_PLAYER_BULLET);
    }

    @Override
    public void postUpdate(double deltaTime) {
        if (ignoreOwnerTime > 0.0) {
            ignoreOwnerTime -= deltaTime;
        }
        ttl -= deltaTime;
        if (ttl <= 0) {
            destroy();
        }
    }

    @Override
    public void onCollision(Entity entity) {
        if (entity == owner && ignoreOwnerTime > 0) {
            return;
        }

        entity.takeDamage(this);

        destroy(); // La bala al chocar se destruye
        System.out.print("Bullet colisiona con " + entity.getClass().getSimpleName());
        System.out.println(entity);
    }
}
