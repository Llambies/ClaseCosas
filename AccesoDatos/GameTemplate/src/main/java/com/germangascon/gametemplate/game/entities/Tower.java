package com.germangascon.gametemplate.game.entities;

import com.germangascon.gametemplate.core.Timer;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.game.TowerType;
import com.germangascon.gametemplate.math.Vector2;

import java.util.Optional;

/**
 * <p><strong>Tower</strong></p>
 * <p>Descripción</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-13<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class Tower extends Entity {
    private float cooldown;
    private float range;
    private final Timer timer;
    private final TowerType towerType;
    private int level;
    private final int initialCost;

    public Tower(float x, float y, TowerType towerType, int level) {
        super(x, y, 64, 64, 35, 65, 
              calculateHp(towerType, level), 
              calculateDamage(towerType, level), 
              towerType.getSpritePath());
        this.towerType = towerType;
        this.level = level;
        this.initialCost = towerType.getCost();
        this.range = calculateRange(towerType, level);
        this.cooldown = calculateCooldown(towerType, level);
        timer = new Timer();
    }

    // Constructor legacy para compatibilidad
    public Tower(float x, float y, int hp, int damage, float range, float cooldown) {
        super(x, y, 64, 64, 35, 65, hp, damage, "/tilesheet/tower");
        this.towerType = TowerType.BASIC;
        this.level = 1;
        this.initialCost = TowerType.BASIC.getCost();
        this.range = range;
        this.cooldown = cooldown;
        timer = new Timer();
    }

    private static int calculateHp(TowerType type, int level) {
        return (int) (type.getHp() * (1 + (level - 1) * 0.3f));
    }

    private static int calculateDamage(TowerType type, int level) {
        return (int) (type.getDamage() * (1 + (level - 1) * 0.4f));
    }

    private static float calculateRange(TowerType type, int level) {
        return type.getRange() * (1 + (level - 1) * 0.1f);
    }

    private static float calculateCooldown(TowerType type, int level) {
        return Math.max(0.1f, type.getCooldown() * (1 - (level - 1) * 0.1f));
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public int getLevel() {
        return level;
    }

    public int getInitialCost() {
        return initialCost;
    }

    /**
     * Mejora la torreta al siguiente nivel
     */
    public void upgrade() {
        level++;
        // Actualizar estadísticas según el nuevo nivel
        maxHp = calculateHp(towerType, level);
        hp = maxHp; // Restaurar HP completo al mejorar
        damage = calculateDamage(towerType, level);
        range = calculateRange(towerType, level);
        cooldown = calculateCooldown(towerType, level);
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    @Override
    public void onCollision(Entity entity) {

    }

    @Override
    public void update(double deltaTime) {
        timer.update(deltaTime);
        if (timer.every(cooldown)) {
            Optional<Tank> optionalTank = gameContext.findNearestEntity(Tank.class, position, Entity::isAlive);
            if (optionalTank.isPresent()) {
                Tank tank = optionalTank.get();
                float distance = tank.getPosition().distance(position);
                if (distance <= range) {
                    Vector2 tankDirection = new Vector2(tank.getDirection());
                    tankDirection.scl(tank.getSpeed());
                    float t = interceptTime(position, tank.getPosition(), tankDirection, Bullet.BULLET_SPEED);

                    // Si no hay solución (bala demasiado lenta o raíces negativas), dispara “a lo tonto”
                    Vector2 aim;
                    if (t <= 0f) {
                        aim = tank.getPosition();
                    } else {
                        aim = new Vector2(tank.getPosition()).mulAdd(tankDirection, t);
                    }
                    gameContext.getEntityFactory().spawnBullet(this, position.x, position.y, aim, 1);
                }
            }
        }
    }

    // Calcula el tiempo que tardará en interceptar el objetivo
    private static float interceptTime(Vector2 shooterPos, Vector2 targetPos, Vector2 targetVel, float projectileSpeed) {
        float rx = targetPos.x - shooterPos.x;
        float ry = targetPos.y - shooterPos.y;

        float vx = targetVel.x;
        float vy = targetVel.y;

        float s2 = projectileSpeed * projectileSpeed;
        float v2 = vx * vx + vy * vy;

        float a = v2 - s2;
        float b = 2f * (rx * vx + ry * vy);
        float c = rx * rx + ry * ry;

        final float EPS = 1e-6f;

        // Caso casi lineal
        if (Math.abs(a) < EPS) {
            if (Math.abs(b) < EPS) return 0f;
            float t = -c / b;
            return (t > 0f) ? t : 0f;
        }

        float disc = b * b - 4f * a * c;
        if (disc < 0f) return 0f;

        float sqrt = (float) Math.sqrt(disc);
        float t1 = (-b - sqrt) / (2f * a);
        float t2 = (-b + sqrt) / (2f * a);

        float t = Float.POSITIVE_INFINITY;
        if (t1 > 0f) t = t1;
        if (t2 > 0f && t2 < t) t = t2;

        return Float.isFinite(t) ? t : 0f;
    }
}
