package com.germangascon.gametemplate.game;

/**
 * <p><strong>EnemyType</strong></p>
 * <p>Define los diferentes tipos de enemigos disponibles en el juego</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-15<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public enum EnemyType {
    BASIC("Básico", 5, 1, 170, "/tilesheet/enemy_basic", 10),
    FAST("Rápido", 3, 1, 250, "/tilesheet/enemy_fast", 15),
    HEAVY("Pesado", 10, 2, 120, "/tilesheet/enemy_heavy", 20),
    ARMORED("Blindado", 15, 2, 100, "/tilesheet/enemy_armored", 30);

    private final String name;
    private final int baseHp;
    private final int baseDamage;
    private final float baseVelocity;
    private final String spritePath;
    private final int baseReward;

    EnemyType(String name, int baseHp, int baseDamage, float baseVelocity, String spritePath, int baseReward) {
        this.name = name;
        this.baseHp = baseHp;
        this.baseDamage = baseDamage;
        this.baseVelocity = baseVelocity;
        this.spritePath = spritePath;
        this.baseReward = baseReward;
    }

    public String getName() {
        return name;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public float getBaseVelocity() {
        return baseVelocity;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public int getBaseReward() {
        return baseReward;
    }

    /**
     * Calcula las estadísticas del enemigo según su tipo y nivel
     * @param level Nivel del enemigo (1-6)
     * @return Array con [hp, damage, velocity, reward]
     */
    public int[] calculateStats(int level) {
        // Las estadísticas aumentan con el nivel
        float multiplier = 1.0f + (level - 1) * 0.5f;
        int hp = (int) (baseHp * multiplier);
        int damage = (int) (baseDamage * multiplier);
        float velocity = baseVelocity * (1.0f + (level - 1) * 0.1f);
        int reward = (int) (baseReward * multiplier);
        
        return new int[]{hp, damage, (int) velocity, reward};
    }
}

