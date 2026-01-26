package com.germangascon.gametemplate.game;

/**
 * <p><strong>TowerType</strong></p>
 * <p>Define los diferentes tipos de torretas disponibles en el juego</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-15<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public enum TowerType {
    BASIC("Básica", 10, 10, 1, 300, 0.8f, "/tilesheet/tower_basic"),
    RAPID("Rápida", 15, 8, 1, 250, 0.4f, "/tilesheet/tower_rapid"),
    HEAVY("Pesada", 25, 15, 2, 400, 1.2f, "/tilesheet/tower_heavy");

    private final String name;
    private final int cost;
    private final int hp;
    private final int damage;
    private final float range;
    private final float cooldown;
    private final String spritePath;

    TowerType(String name, int cost, int hp, int damage, float range, float cooldown, String spritePath) {
        this.name = name;
        this.cost = cost;
        this.hp = hp;
        this.damage = damage;
        this.range = range;
        this.cooldown = cooldown;
        this.spritePath = spritePath;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public float getRange() {
        return range;
    }

    public float getCooldown() {
        return cooldown;
    }

    public String getSpritePath() {
        return spritePath;
    }

    /**
     * Calcula el costo de mejorar una torreta de un nivel a otro
     * @param currentLevel Nivel actual de la torreta
     * @return Costo de la mejora
     */
    public int getUpgradeCost(int currentLevel) {
        // El costo de mejora es el 50% del costo base multiplicado por el nivel actual
        return (int) (cost * 0.5f * currentLevel);
    }

    /**
     * Calcula el reembolso al derribar una torreta
     * @param initialCost Costo inicial de la torreta
     * @return Cantidad de dinero a reembolsar (mitad del costo)
     */
    public static int getRefundAmount(int initialCost) {
        return initialCost / 2;
    }
}

