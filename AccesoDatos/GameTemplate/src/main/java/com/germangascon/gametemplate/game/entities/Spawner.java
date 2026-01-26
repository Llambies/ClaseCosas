package com.germangascon.gametemplate.game.entities;

import com.germangascon.gametemplate.core.Timer;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.game.WaveManager;

/**
 * <p><strong>Spawner</strong></p>
 * <p>Descripción</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-15<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class Spawner extends Entity {
    private Class<? extends Entity> entityClass;
    private float cooldown;
    private int level;
    private final Timer timer;

    public Spawner(Class<? extends Entity> entityClass, float x, float y, int hp, int damage, float cooldown, int level) {
        super(x, y, 60, 100, 35, 65, hp, damage, null);
        this.entityClass = entityClass;
        this.level = level;
        this.cooldown = cooldown;
        timer = new Timer();
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public void onCollision(Entity entity) {

    }

    @Override
    public void update(double deltaTime) {
        timer.update(deltaTime);
        if (timer.every(cooldown)) {
            if (entityClass == Tank.class) {
                // Verificar si se puede spawneear más enemigos según el WaveManager
                WaveManager waveManager = gameContext.getWaveManager();
                if (waveManager != null && !waveManager.canSpawnEnemy()) {
                    return; // No spawneear si no se puede
                }
                
                gameContext.getEntityFactory().spawnTank(this.getX(), this.getY(), level);
            } else {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
    }
}
