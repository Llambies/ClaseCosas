package com.germangascon.gametemplate.game;

import java.awt.Color;

/**
 * <p><strong>EnemyLevel</strong></p>
 * <p>Define los niveles de enemigos con sus colores de tinte</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-15<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public enum EnemyLevel {
    LEVEL_1(1, new Color(128, 128, 128, 150)),      // Gris
    LEVEL_2(2, new Color(0, 255, 0, 150)),          // Verde
    LEVEL_3(3, new Color(0, 150, 255, 150)),        // Azul
    LEVEL_4(4, new Color(200, 0, 255, 150)),        // Morado
    LEVEL_5(5, new Color(255, 255, 0, 150)),        // Amarillo
    LEVEL_6(6, new Color(255, 0, 0, 150));          // Rojo

    private final int level;
    private final Color tintColor;

    EnemyLevel(int level, Color tintColor) {
        this.level = level;
        this.tintColor = tintColor;
    }

    public int getLevel() {
        return level;
    }

    public Color getTintColor() {
        return tintColor;
    }

    /**
     * Obtiene el nivel de enemigo basado en el número de oleada
     * @param wave Número de oleada
     * @return EnemyLevel correspondiente
     */
    public static EnemyLevel fromWave(int wave) {
        // Los niveles aumentan con las oleadas
        int level = Math.min(6, 1 + (wave - 1) / 3); // Cada 3 oleadas sube un nivel
        return getByLevel(level);
    }

    /**
     * Obtiene el EnemyLevel por su número de nivel
     * @param level Número de nivel (1-6)
     * @return EnemyLevel correspondiente
     */
    public static EnemyLevel getByLevel(int level) {
        for (EnemyLevel enemyLevel : values()) {
            if (enemyLevel.level == level) {
                return enemyLevel;
            }
        }
        return LEVEL_1; // Por defecto nivel 1
    }
}

