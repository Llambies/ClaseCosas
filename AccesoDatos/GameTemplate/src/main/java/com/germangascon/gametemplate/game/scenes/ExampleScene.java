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

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.awt.event.KeyEvent;
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
    private Level currentLevel;
    private HashMap<GridPos, String> levelTiles;
    private List<GridPos> levelWaypoints;
    private TowerType selectedTowerType = TowerType.BASIC;

    // Modificar el constructor
    public ExampleScene() {
        this.economy = Economy.getInstance();
        this.levelRepository = new LevelRepository();
        this.levelTiles = new HashMap<>();
        this.levelWaypoints = new ArrayList<>();
        // Cargar nivel por defecto (puedes cambiar el nombre)
        loadLevel("nivel_1"); // O el nombre que prefieras

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
                    { "/tilesheet/tower", "42", "21", "0" },
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
        
        // Mostrar tipo de torreta seleccionada
        g.setFont(new Font("Rubik", Font.BOLD, 18));
        g.drawString("Torreta: " + selectedTowerType.getName() + " (Costo: " + selectedTowerType.getCost() + ")", 10, 80);
        
        // Mostrar controles
        g.setFont(new Font("Rubik", Font.PLAIN, 14));
        g.drawString("1-3: Seleccionar torreta | Click Izq: Colocar/Mejorar | Click Der: Derribar", 10, engine.getHeight() - 20);

        // Entidades
        for (Entity entity : getEntities()) {
            drawEntity(g, entity);
        }
    }
}
