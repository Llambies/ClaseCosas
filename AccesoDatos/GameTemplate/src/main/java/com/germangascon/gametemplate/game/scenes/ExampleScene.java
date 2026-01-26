package com.germangascon.gametemplate.game.scenes;

import com.germangascon.gametemplate.core.Config;
import com.germangascon.gametemplate.core.GameScene;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.game.EntityFactory;
import com.germangascon.gametemplate.game.entities.Tank;
import com.germangascon.gametemplate.game.entities.Tower;
import com.germangascon.gametemplate.game.TowerType;
import com.germangascon.gametemplate.math.Vector2;
import com.germangascon.gametemplate.game.Economy;
import com.germangascon.gametemplate.math.GridPos;
import com.germangascon.gametemplate.game.Level;
import com.germangascon.gametemplate.game.LevelRepository;
import com.germangascon.gametemplate.game.WaveManager;
import com.germangascon.gametemplate.game.GameSave;
import com.germangascon.gametemplate.game.GameSaveManager;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.FontMetrics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * <strong>Game</strong>
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
public class ExampleScene extends GameScene {
    private EntityFactory entityFactory;
    private final Economy economy;
    private LevelRepository levelRepository;
    private GameSaveManager gameSaveManager;
    private Level currentLevel;
    private HashMap<GridPos, String> levelTiles;
    private List<GridPos> levelWaypoints;
    private TowerType selectedTowerType = TowerType.BASIC;
    private WaveManager waveManager;
    private boolean wasWaveInProgress; // Para detectar cuando se completa una oleada

    // Modificar el constructor
    public ExampleScene() {
        this.economy = Economy.getInstance();
        this.levelRepository = new LevelRepository();
        this.gameSaveManager = new GameSaveManager();
        this.levelTiles = new HashMap<>();
        this.levelWaypoints = new ArrayList<>();
        this.waveManager = new WaveManager(this);
        this.wasWaveInProgress = false;
        // No cargar nivel por defecto - se cargará desde el menú o desde un estado guardado
    }

    @Override
    protected void reset() {
        super.reset();
        spawnEntities();
    }

    @Override
    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    @Override
    public WaveManager getWaveManager() {
        return waveManager;
    }

    @Override
    protected void update(double deltaTime) {
        super.update(deltaTime);
        // Actualizar el gestor de oleadas con el deltaTime escalado
        if (waveManager != null) {
            double scaledDeltaTime = deltaTime * getTimeScale();
            waveManager.update(scaledDeltaTime);
            
            // Detectar cuando se completa una oleada para guardar automáticamente
            boolean isWaveInProgress = waveManager.isWaveInProgress();
            if (wasWaveInProgress && !isWaveInProgress && waveManager.isWaitingForNextWave()) {
                // La oleada acaba de completarse
                autoSaveGame();
            }
            wasWaveInProgress = isWaveInProgress;
        }
    }

    private void spawnEntities() {
        /*
         * waypoints.add(new Vector2(210, 445));
         * waypoints.add(new Vector2(600, 445));
         */
        GridPos gridPos = new GridPos(3, 0);
        entityFactory.spawnSpawner(Tank.class, gridPos.getCentro().x, gridPos.getCentro().y, 2, 1);
        // entityFactory.spawnTank(210, 0, 1);
        // entityFactory.spawnTank(800, 500, 1);
    }

    @Override
    public void loadAssets() {
        try {
            assetManager.loadSpriteFromTilesheet("/tilesheet/colored_packed.png",
                    "/tilesheet/background",
                    0,
                    0,
                    Config.TILE_SIZE / 4,
                    Config.TILE_SIZE / 4);

            // O cargar múltiples sprites a la vez
            String[][] sprites = {
                    { "/tilesheet/tank", "46", "0", "270" },
                    { "/tilesheet/enemy_basic", "46", "0", "270" },      // Enemigo básico (mismo que tank por ahora)
                    { "/tilesheet/enemy_fast", "45", "0", "270" },       // Enemigo rápido (sprite diferente)
                    { "/tilesheet/enemy_heavy", "47", "0", "270" },      // Enemigo pesado (sprite diferente)
                    { "/tilesheet/enemy_armored", "48", "0", "270" },    // Enemigo blindado (sprite diferente)
                    { "/tilesheet/tower", "42", "21", "0" },              // Torre genérica (para compatibilidad)
                    { "/tilesheet/tower_basic", "42", "21", "0" },       // Torre básica
                    { "/tilesheet/tower_rapid", "44", "21", "0" },       // Torre rápida (sprite diferente)
                    { "/tilesheet/tower_heavy", "43", "21", "0" },       // Torre pesada (sprite diferente)
                    { "/tilesheet/path", "11", "1", "0" },
                    { "/tilesheet/bullet", "40", "5", "45" },
                    { "/cursor/base", "35", "10", "0" },
            };
            assetManager.loadSpritesFromTilesheet(
                    "/tilesheet/colored-transparent_packed.png",
                    Config.TILE_SIZE / 4,
                    Config.TILE_SIZE / 4,
                    sprites);

        } catch (IOException e) {
            throw new RuntimeException("Error cargando assets", e);
        }
    }

    @Override
    protected void init(com.germangascon.gametemplate.core.Engine engine) {
        super.init(engine);
        // Configurar el cursor después de que el engine esté inicializado
        // Usar un pequeño delay para asegurar que el engine esté completamente inicializado
        new Thread(() -> {
            try {
                Thread.sleep(100); // Pequeño delay para asegurar inicialización
                setupCustomCursor();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void setupCustomCursor() {
        // Obtener el sprite del assetManager
        BufferedImage cursorImage = assetManager.getSprite("/cursor/base");
        // O si cargaste uno específico para cursor:
        // BufferedImage cursorImage = assetManager.getSprite("/cursor/custom");

        if (cursorImage != null && engine != null) {
            // Definir el punto de clic (centro del sprite)
            Point hotSpot = new Point(
                    cursorImage.getWidth() / 2,
                    cursorImage.getHeight() / 2);

            // Crear el cursor personalizado
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    cursorImage,
                    hotSpot,
                    "CustomCursor");

            // Aplicar el cursor al Engine
            engine.setCursor(customCursor);
        }
    }

    @Override
    public void processInput() {
        List<Entity> entities = getEntities();
        int mouseX = inputManager.getMouseX();
        int mouseY = inputManager.getMouseY();

        if (Config.SHOW_DEBUG) {
            addDebugInfo("Mouse", "(" + mouseX + ", " + mouseY + ")");
        }

        if (inputManager.hasLeftClick()) {
            if (Config.SHOW_DEBUG) {
                addDebugInfo("LeftClick", "(" + mouseX + ", " + mouseY + ")");
            }

            // Verificar si se hizo clic en el botón de iniciar oleada
            if (waveManager != null && waveManager.isWaitingForNextWave()) {
                int buttonX = 10;
                int buttonY = 160;
                int buttonWidth = 200;
                int buttonHeight = 40;
                
                if (mouseX >= buttonX && mouseX <= buttonX + buttonWidth &&
                    mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {
                    waveManager.forceStartNextWave();
                    System.out.println("Oleada iniciada manualmente");
                    return; // No procesar otros clics
                }
            }

            if (!entities.isEmpty() && entities.getFirst() instanceof Tank entity) {
                entityFactory.spawnBullet(entity, entity.getX(), entity.getY(), new Vector2(mouseX, mouseY), 1);
            }

            // Verificar si hay una torreta en la posición clicada
            Tower clickedTower = findTowerAtPosition(mouseX, mouseY);
            if (clickedTower != null) {
                // Si hay una torreta, intentar mejorarla
                int upgradeCost = clickedTower.getTowerType().getUpgradeCost(clickedTower.getLevel());
                if (economy.tengoSuficienteDinero(upgradeCost)) {
                    clickedTower.upgrade();
                    economy.gastarDinero(upgradeCost);
                    System.out.println("Torreta mejorada a nivel " + clickedTower.getLevel());
                } else {
                    System.out.println("No tienes suficiente dinero para mejorar. Costo: " + upgradeCost);
                }
                return;
            }

            // Si no hay torreta, intentar colocar una nueva
            int towerCost = selectedTowerType.getCost();
            if (economy.tengoSuficienteDinero(towerCost)) {
                Vector2 position = new Vector2(mouseX, mouseY);
                GridPos gridPos = new GridPos(position);
                
                // Verificar que no haya otra torreta en esa posición
                if (findTowerAtPosition(mouseX, mouseY) != null) {
                    System.out.println("Ya hay una torreta en esa posición");
                    return;
                }
                
                if (levelTiles.containsKey(gridPos)) {
                    String tile = levelTiles.get(gridPos);
                    if (tile.equals("/tilesheet/path")) {
                        System.out.println("No se puede colocar una torreta en el camino");
                        return;
                    }
                }

                entityFactory.spawnTower(gridPos.getCentro().x, gridPos.getCentro().y, selectedTowerType, 1);
                economy.gastarDinero(towerCost);
                System.out.println("Torreta " + selectedTowerType.getName() + " colocada. Costo: " + towerCost);
            } else {
                System.out.println("No tienes suficiente dinero. Costo: " + towerCost);
            }
        }

        if (inputManager.hasMiddleClick()) {
            if (Config.SHOW_DEBUG) {
                addDebugInfo("MiddleClick", "(" + mouseX + ", " + mouseY + ")");
            }
            if (entities.size() > 1 && entities.getFirst() instanceof Tank entity) {
                entityFactory.spawnBullet(entity, entity.getX(), entity.getY(), entities.get(1).getPosition(), 1);
            }
        }

        if (inputManager.hasRightClick()) {
            if (Config.SHOW_DEBUG) {
                addDebugInfo("RightClick", "(" + mouseX + ", " + mouseY + ")");
            }
            
            // Derribar torreta si hay una en la posición clicada
            Tower clickedTower = findTowerAtPosition(mouseX, mouseY);
            if (clickedTower != null) {
                int refund = TowerType.getRefundAmount(clickedTower.getInitialCost());
                economy.ganarDinero(refund);
                clickedTower.destroy();
                System.out.println("Torreta derribada. Reembolso: " + refund);
                return;
            }
            
            // Ejemplo: mover la primera entidad dinámica al punto clicado
            if (!entities.isEmpty() && entities.getFirst() instanceof Tank entity) {
                entity.getPosition().set(mouseX, mouseY);
                // entity.goToNearestWaypoint();
                entity.goToNearestWaypointNoBacktrack();
                /*
                 * entity.getPosition().set(mouseX, mouseY);
                 * entity.reset();
                 */
            }
        }

        if (inputManager.isKeyJustPressed(KeyEvent.VK_G)) {
            Config.SHOW_GRID = !Config.SHOW_GRID;
        }

        if (inputManager.isKeyJustPressed(KeyEvent.VK_F3)) {
            Config.SHOW_DEBUG = !Config.SHOW_DEBUG;
        }

        if (inputManager.isKeyJustPressed(KeyEvent.VK_F4)) {
            Config.SHOW_HITBOXES = !Config.SHOW_HITBOXES;
        }

        if (inputManager.isKeyJustPressed(KeyEvent.VK_R)) {
            reset();
        }

        if (inputManager.isKeyJustPressed(KeyEvent.VK_P)) {
            pause();
        }

        // Guardar partida manualmente con F5 (guardado adicional al automático)
        if (inputManager.isKeyJustPressed(KeyEvent.VK_F5)) {
            saveGameState();
            System.out.println("Partida guardada manualmente");
        }

        // Volver al menú con ESC
        if (inputManager.isKeyJustPressed(KeyEvent.VK_ESCAPE)) {
            returnToMainMenu();
        }

        // Selección de tipo de torreta con teclas 1, 2, 3
        if (inputManager.isKeyJustPressed(KeyEvent.VK_1)) {
            selectedTowerType = TowerType.BASIC;
            System.out.println("Torreta seleccionada: " + selectedTowerType.getName() + " (Costo: " + selectedTowerType.getCost() + ")");
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_2)) {
            selectedTowerType = TowerType.RAPID;
            System.out.println("Torreta seleccionada: " + selectedTowerType.getName() + " (Costo: " + selectedTowerType.getCost() + ")");
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_3)) {
            selectedTowerType = TowerType.HEAVY;
            System.out.println("Torreta seleccionada: " + selectedTowerType.getName() + " (Costo: " + selectedTowerType.getCost() + ")");
        }

        // Control de velocidad con + y -
        if (inputManager.isKeyJustPressed(KeyEvent.VK_PLUS) || inputManager.isKeyJustPressed(KeyEvent.VK_EQUALS)) {
            increaseTimeScale(0.5);
            System.out.println("Velocidad: " + String.format("%.1f", getTimeScale()) + "x");
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_MINUS)) {
            decreaseTimeScale(0.5);
            System.out.println("Velocidad: " + String.format("%.1f", getTimeScale()) + "x");
        }
        // Restablecer velocidad a normal con 0
        if (inputManager.isKeyJustPressed(KeyEvent.VK_0)) {
            resetTimeScale();
            System.out.println("Velocidad restablecida a 1.0x");
        }
    }

    /**
     * Encuentra una torreta en la posición especificada
     * @param x Coordenada X
     * @param y Coordenada Y
     * @return La torreta encontrada o null si no hay ninguna
     */
    private Tower findTowerAtPosition(int x, int y) {
        Vector2 clickPos = new Vector2(x, y);
        for (Entity entity : getEntities()) {
            if (entity instanceof Tower tower) {
                float distance = tower.getPosition().distance(clickPos);
                // Considerar que el clic está dentro si está dentro del área de la torreta
                if (distance < tower.getWidth() / 2f) {
                    return tower;
                }
            }
        }
        return null;
    }

    /**
     * Carga un nivel desde MongoDB y lo aplica a la escena
     */
    public void loadLevel(String name) {
        Level level = levelRepository.findByName(name);
        if (level == null) {
            System.out.println("Nivel no encontrado: " + name + ". Usando configuración por defecto.");
            return;
        }

        currentLevel = level;

        // Limpiar estado actual
        levelTiles.clear();
        levelWaypoints.clear();

        // Cargar tiles
        for (Level.TilePosition tile : level.getTiles().values()) {
            GridPos gridPos = new GridPos(tile.getX(), tile.getY());
            levelTiles.put(gridPos, tile.getSpritePath());
        }

        // Cargar waypoints
        for (Level.WaypointPosition waypoint : level.getWaypoints()) {
            levelWaypoints.add(new GridPos(waypoint.getX(), waypoint.getY()));
        }

        // Actualizar waypoints del EntityFactory
        entityFactory = new EntityFactory(this, levelWaypoints);
        spawnEntities();
        
        // Inicializar el estado de seguimiento de oleadas
        wasWaveInProgress = waveManager != null && waveManager.isWaveInProgress();

        System.out.println("Nivel cargado: " + name);
    }

    @Override
    public void draw(Graphics2D g) {
        // Fondo
        Image bg = assetManager.getSprite("/tilesheet/background");
        if (bg != null) {
            g.drawImage(bg, 0, 0, engine.getWidth(), engine.getHeight(), null);
        }

        // Dibujar tiles del nivel cargado
        for (Map.Entry<GridPos, String> entry : levelTiles.entrySet()) {
            GridPos gridPos = entry.getKey();
            Image tile = assetManager.getSprite(entry.getValue());
            if (tile != null) {
                g.drawImage(tile, (int) gridPos.getTopLeft().x, (int) gridPos.getTopLeft().y,
                        Config.TILE_SIZE, Config.TILE_SIZE, null);
            }
        }

        // UI
        g.setColor(Color.WHITE);
        g.setFont(new Font("Rubik", Font.BOLD, 24));
        g.drawString("Money: " + economy.getDinero(), 10, 50);
        
        // Mostrar información de oleadas
        if (waveManager != null) {
            g.setFont(new Font("Rubik", Font.BOLD, 20));
            g.drawString("Oleada: " + waveManager.getCurrentWave(), 10, 110);
            
            if (waveManager.isWaveInProgress()) {
                g.setFont(new Font("Rubik", Font.PLAIN, 16));
                g.drawString("Enemigos restantes: " + waveManager.getEnemiesRemaining(), 10, 135);
                g.drawString("Por spawneear: " + waveManager.getEnemiesRemainingToSpawn(), 10, 155);
            } else if (waveManager.isWaitingForNextWave()) {
                g.setFont(new Font("Rubik", Font.PLAIN, 16));
                int timeLeft = (int) Math.ceil(waveManager.getTimeUntilNextWave());
                g.drawString("Siguiente oleada en: " + timeLeft + "s", 10, 135);
                
                // Dibujar botón para iniciar oleada
                int buttonX = 10;
                int buttonY = 160;
                int buttonWidth = 200;
                int buttonHeight = 40;
                
                // Fondo del botón
                g.setColor(new Color(0, 150, 0, 200));
                g.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
                
                // Borde del botón
                g.setColor(Color.WHITE);
                g.setStroke(new java.awt.BasicStroke(2));
                g.drawRect(buttonX, buttonY, buttonWidth, buttonHeight);
                
                // Texto del botón
                g.setFont(new Font("Rubik", Font.BOLD, 18));
                g.setColor(Color.WHITE);
                String buttonText = "Iniciar Siguiente Oleada";
                FontMetrics fm = g.getFontMetrics();
                int textX = buttonX + (buttonWidth - fm.stringWidth(buttonText)) / 2;
                int textY = buttonY + (buttonHeight + fm.getAscent() - fm.getDescent()) / 2;
                g.drawString(buttonText, textX, textY);
            }
        }
        
        // Mostrar tipo de torreta seleccionada
        g.setFont(new Font("Rubik", Font.BOLD, 18));
        g.drawString("Torreta: " + selectedTowerType.getName() + " (Costo: " + selectedTowerType.getCost() + ")", 10, 190);
        
        // Mostrar velocidad del juego
        g.setFont(new Font("Rubik", Font.BOLD, 18));
        String speedText = "Velocidad: " + String.format("%.1f", getTimeScale()) + "x";
        g.drawString(speedText, 10, 220);
        
        // Mostrar controles
        g.setFont(new Font("Rubik", Font.PLAIN, 14));
        g.drawString("1-3: Seleccionar torreta | Click Izq: Colocar/Mejorar | Click Der: Derribar | F5: Guardar | ESC: Menú", 10, engine.getHeight() - 40);
        g.drawString("+/-: Cambiar velocidad | 0: Velocidad normal", 10, engine.getHeight() - 20);

        // Entidades
        for (Entity entity : getEntities()) {
            drawEntity(g, entity);
        }
    }

    /**
     * Carga un estado guardado de partida
     */
    public void loadGameState(GameSave save) {
        if (save == null || currentLevel == null) {
            System.out.println("No se puede cargar el estado: partida o nivel nulo");
            return;
        }

        // Restaurar dinero - primero establecer a 0 y luego agregar el dinero guardado
        int currentMoney = economy.getDinero();
        if (currentMoney != save.getMoney()) {
            if (currentMoney > save.getMoney()) {
                // Si tenemos más dinero, gastar la diferencia
                economy.gastarDinero(currentMoney - save.getMoney());
            } else {
                // Si tenemos menos, ganar la diferencia
                economy.ganarDinero(save.getMoney() - currentMoney);
            }
        }

        // Restaurar oleada
        if (waveManager != null) {
            waveManager.setCurrentWave(save.getCurrentWave());
            // Inicializar el estado de seguimiento de oleadas
            wasWaveInProgress = waveManager.isWaveInProgress();
        }

        // Restaurar torretas
        for (GameSave.TowerSave towerSave : save.getTowers()) {
            try {
                TowerType towerType = TowerType.valueOf(towerSave.getTowerType());
                // Crear la torreta con el nivel correcto
                Tower tower = new Tower(towerSave.getX(), towerSave.getY(), towerType, 1);
                // Mejorar la torreta al nivel guardado si es mayor que 1
                for (int i = 1; i < towerSave.getLevel(); i++) {
                    tower.upgrade();
                }
                spawn(tower);
            } catch (IllegalArgumentException e) {
                System.out.println("Error al cargar torreta: tipo de torreta inválido " + towerSave.getTowerType());
            }
        }

        System.out.println("Estado de partida cargado: Oleada " + save.getCurrentWave() + 
                          ", Dinero: " + save.getMoney() + 
                          ", Torretas: " + save.getTowers().size());
    }

    /**
     * Guarda el estado actual de la partida
     */
    public void saveGameState() {
        if (currentLevel == null) {
            System.out.println("No se puede guardar: no hay nivel cargado");
            return;
        }

        GameSave save = new GameSave();
        save.setLevelName(currentLevel.getName());
        save.setCurrentWave(waveManager != null ? waveManager.getCurrentWave() : 0);
        save.setMoney(economy.getDinero());

        // Guardar todas las torretas
        List<GameSave.TowerSave> towers = new ArrayList<>();
        for (Entity entity : getEntities()) {
            if (entity instanceof Tower tower) {
                GameSave.TowerSave towerSave = new GameSave.TowerSave(
                    tower.getX(),
                    tower.getY(),
                    tower.getTowerType().name(),
                    tower.getLevel()
                );
                towers.add(towerSave);
            }
        }
        save.setTowers(towers);

        // Guardar en MongoDB
        gameSaveManager.saveGame(save);
        System.out.println("Partida guardada: " + currentLevel.getName() + 
                          " (Oleada: " + save.getCurrentWave() + 
                          ", Dinero: " + save.getMoney() + 
                          ", Torretas: " + towers.size() + ")");
    }

    /**
     * Guarda automáticamente la partida al completar una oleada
     */
    private void autoSaveGame() {
        if (currentLevel == null) {
            return; // No guardar si no hay nivel cargado
        }
        saveGameState();
        System.out.println("Partida guardada automáticamente al completar oleada " + waveManager.getCurrentWave());
    }

    /**
     * Vuelve al menú principal
     */
    private void returnToMainMenu() {
        MainMenuScene menuScene = new MainMenuScene();
        engine.setScene(menuScene);
    }
}
