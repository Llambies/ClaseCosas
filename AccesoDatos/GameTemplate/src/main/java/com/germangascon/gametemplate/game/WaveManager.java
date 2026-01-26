package com.germangascon.gametemplate.game;

import com.germangascon.gametemplate.core.GameScene;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.game.entities.Tank;

import java.util.List;

/**
 * <p><strong>WaveManager</strong></p>
 * <p>Gestiona el sistema de oleadas del juego</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-15<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class WaveManager {
    private final GameScene gameScene;
    private int currentWave;
    private int enemiesInWave;
    private int enemiesSpawned;
    private int enemiesRemaining;
    private boolean waveInProgress;
    private double timeBetweenWaves;
    private double waveCooldownTimer;
    private boolean waitingForNextWave;

    public WaveManager(GameScene gameScene) {
        this.gameScene = gameScene;
        this.currentWave = 0;
        this.enemiesInWave = 0;
        this.enemiesSpawned = 0;
        this.enemiesRemaining = 0;
        this.waveInProgress = false;
        this.timeBetweenWaves = 5.0; // 5 segundos entre oleadas
        this.waveCooldownTimer = 0.0;
        this.waitingForNextWave = false;
    }

    /**
     * Inicia la siguiente oleada
     */
    public void startNextWave() {
        if (waveInProgress) {
            return; // No iniciar si hay una oleada en progreso
        }

        currentWave++;
        // Calcular número de enemigos: aumenta con cada oleada
        enemiesInWave = calculateEnemiesForWave(currentWave);
        enemiesSpawned = 0;
        enemiesRemaining = 0; // Inicializar en 0, se actualizará cuando se spawneen enemigos
        waveInProgress = true;
        waitingForNextWave = false;
        waveCooldownTimer = 0.0;

        System.out.println("¡Oleada " + currentWave + " iniciada! Enemigos: " + enemiesInWave);
    }

    /**
     * Fuerza el inicio de la siguiente oleada manualmente
     * Útil cuando el jugador quiere saltar el tiempo de espera
     */
    public void forceStartNextWave() {
        if (waveInProgress) {
            return; // No iniciar si hay una oleada en progreso
        }
        startNextWave();
    }

    /**
     * Calcula el número de enemigos para una oleada específica
     * @param wave Número de oleada
     * @return Número de enemigos
     */
    private int calculateEnemiesForWave(int wave) {
        // Fórmula: 5 + (oleada * 3)
        return 5 + (wave * 3);
    }

    /**
     * Notifica que se ha spawneado un enemigo
     */
    public void onEnemySpawned() {
        if (waveInProgress) {
            enemiesSpawned++;
            // Incrementar el contador de enemigos restantes cuando se spawnea uno
            enemiesRemaining++;
        }
    }

    /**
     * Notifica que un enemigo ha sido eliminado
     * Nota: No completa la oleada aquí, eso se hace en updateEnemiesRemaining()
     * para verificar que todos los enemigos hayan sido spawneados
     */
    public void onEnemyDestroyed() {
        // No hacer nada aquí, updateEnemiesRemaining() se encargará de contar los enemigos vivos
        // y completar la oleada cuando corresponda
    }

    /**
     * Completa la oleada actual
     */
    private void completeWave() {
        waveInProgress = false;
        waitingForNextWave = true;
        waveCooldownTimer = 0.0;
        System.out.println("¡Oleada " + currentWave + " completada!");
    }

    /**
     * Actualiza el gestor de oleadas
     * @param deltaTime Tiempo transcurrido desde el último frame
     */
    public void update(double deltaTime) {
        if (waitingForNextWave) {
            waveCooldownTimer += deltaTime;
            // Ya no inicia automáticamente, solo actualiza el timer
            // El jugador debe usar el botón para iniciar
        } else if (!waveInProgress && currentWave == 0) {
            // Iniciar la primera oleada automáticamente solo al inicio
            startNextWave();
        }

        // Actualizar contador de enemigos restantes basado en entidades vivas
        if (waveInProgress) {
            updateEnemiesRemaining();
        }
    }

    /**
     * Actualiza el contador de enemigos restantes contando las entidades vivas
     */
    private void updateEnemiesRemaining() {
        List<Entity> entities = gameScene.getEntities();
        int aliveEnemies = 0;
        for (Entity entity : entities) {
            if (entity instanceof Tank && entity.isAlive()) {
                aliveEnemies++;
            }
        }
        enemiesRemaining = aliveEnemies;
        
        // Solo completar la oleada si:
        // 1. No quedan enemigos vivos
        // 2. Todos los enemigos de la oleada ya fueron spawneados
        if (aliveEnemies == 0 && enemiesSpawned >= enemiesInWave && enemiesInWave > 0) {
            completeWave();
        }
    }

    /**
     * Verifica si se puede spawneear más enemigos en la oleada actual
     * @return true si se pueden spawneear más enemigos
     */
    public boolean canSpawnEnemy() {
        return waveInProgress && enemiesSpawned < enemiesInWave;
    }

    /**
     * Obtiene el número de enemigos que quedan por spawneear en la oleada actual
     * @return Número de enemigos restantes por spawneear
     */
    public int getEnemiesRemainingToSpawn() {
        if (!waveInProgress) {
            return 0;
        }
        return Math.max(0, enemiesInWave - enemiesSpawned);
    }

    // Getters
    public int getCurrentWave() {
        return currentWave;
    }

    public int getEnemiesInWave() {
        return enemiesInWave;
    }

    public int getEnemiesRemaining() {
        return enemiesRemaining;
    }

    public boolean isWaveInProgress() {
        return waveInProgress;
    }

    public boolean isWaitingForNextWave() {
        return waitingForNextWave;
    }

    public double getTimeUntilNextWave() {
        return Math.max(0.0, timeBetweenWaves - waveCooldownTimer);
    }

    public void setTimeBetweenWaves(double timeBetweenWaves) {
        this.timeBetweenWaves = timeBetweenWaves;
    }

    /**
     * Establece la oleada actual (útil para cargar partidas guardadas)
     * @param wave Número de oleada a establecer
     */
    public void setCurrentWave(int wave) {
        this.currentWave = wave;
        this.waveInProgress = false;
        this.waitingForNextWave = true;
        this.waveCooldownTimer = 0.0;
        this.enemiesInWave = 0;
        this.enemiesSpawned = 0;
        this.enemiesRemaining = 0;
    }
}

