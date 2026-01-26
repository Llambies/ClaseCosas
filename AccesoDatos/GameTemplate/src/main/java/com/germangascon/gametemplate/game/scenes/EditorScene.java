package com.germangascon.gametemplate.game.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.germangascon.gametemplate.core.Config;
import com.germangascon.gametemplate.core.GameScene;
import com.germangascon.gametemplate.entities.Entity;
import com.germangascon.gametemplate.game.EntityFactory;
import com.germangascon.gametemplate.game.Level;
import com.germangascon.gametemplate.game.LevelRepository;
import com.germangascon.gametemplate.math.GridPos;
import com.germangascon.gametemplate.math.Vector2;

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
public class EditorScene extends GameScene {
    private HashMap<GridPos, String> scenarioGridPositions;
    private int hotbarIndex = 0;
    private String[] hotbarTiles = {
            "/tilesheet/path",
            "/tilesheet/tree",
            "/tilesheet/rock",
            "/tilesheet/bush",
            "/tilesheet/water",
            "/tilesheet/grass",
            "/tilesheet/grass",
            "/tilesheet/grass",
            "/tilesheet/grass",
    };
    private List<GridPos> waypoints;

    public EditorScene() {
        this.scenarioGridPositions = new HashMap<>();
        this.waypoints = new ArrayList<>();
        this.levelRepository = new LevelRepository();
    }

    private LevelRepository levelRepository;

    @Override
    public EntityFactory getEntityFactory() {
        return new EntityFactory(this, waypoints);
    }

    @Override
    public void loadAssets() {
        try {
            // O cargar múltiples sprites a la vez
            String[][] sprites = {
                    { "/tilesheet/path", "4", "0" },
                    { "/tilesheet/tree", "4", "1" },
                    { "/tilesheet/rock", "5", "2" },
                    { "/tilesheet/bush", "6", "2" },
                    { "/tilesheet/water", "8", "5" },
                    { "/tilesheet/grass", "5", "0" },
            };
            assetManager.loadSpritesFromTilesheet(
                    "/tilesheet/colored-transparent_packed.png",
                    Config.TILE_SIZE / 4,
                    Config.TILE_SIZE / 4,
                    sprites);

            String[][] background = {
                { "/tilesheet/background", "0", "0" },
            };

            assetManager.loadSpritesFromTilesheet(
                    "/tilesheet/colored_packed.png",
                    Config.TILE_SIZE / 4,
                    Config.TILE_SIZE / 4,
                    background);


        } catch (IOException e) {
            throw new RuntimeException("Error cargando assets", e);
        }
    }

    @Override
    public void processInput() {
        int mouseX = inputManager.getMouseX();
        int mouseY = inputManager.getMouseY();

        if (Config.SHOW_DEBUG) {
            addDebugInfo("Mouse", "(" + mouseX + ", " + mouseY + ")");
        }

        if (inputManager.hasLeftClick()) {
            if (Config.SHOW_DEBUG) {
                addDebugInfo("LeftClick", "(" + mouseX + ", " + mouseY + ")");
            }

            GridPos gridPos = new GridPos(new Vector2(mouseX, mouseY));
            scenarioGridPositions.put(gridPos, hotbarTiles[hotbarIndex]);

        }

        if (inputManager.hasMiddleClick()) {
            if (Config.SHOW_DEBUG) {
                addDebugInfo("MiddleClick", "(" + mouseX + ", " + mouseY + ")");
            }

            GridPos gridPos = new GridPos(new Vector2(mouseX, mouseY));
            if (!waypoints.contains(gridPos)) {
                waypoints.add(gridPos);
            } else {
                waypoints.remove(gridPos);
            }
        }

        if (inputManager.hasRightClick()) {
            if (Config.SHOW_DEBUG) {
                addDebugInfo("RightClick", "(" + mouseX + ", " + mouseY + ")");
            }

            GridPos gridPos = new GridPos(new Vector2(mouseX, mouseY));
            scenarioGridPositions.remove(gridPos);
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

        if (inputManager.isKeyJustPressed(KeyEvent.VK_1)) {
            hotbarIndex = 0;
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_2)) {
            hotbarIndex = 1;
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_3)) {
            hotbarIndex = 2;
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_4)) {
            hotbarIndex = 3;
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_5)) {
            hotbarIndex = 4;
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_6)) {
            hotbarIndex = 5;
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_7)) {
            hotbarIndex = 6;
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_8)) {
            hotbarIndex = 7;
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_9)) {
            hotbarIndex = 8;
        }

        // Añadir después de los otros controles de teclado (alrededor de la línea 171)

        if (inputManager.isKeyJustPressed(KeyEvent.VK_S) && inputManager.isKeyDown(KeyEvent.VK_CONTROL)) {
            // Guardar nivel (Ctrl+S)
            // Por ahora guarda con un nombre por defecto
            saveLevel("nivel_1");
        }

        if (inputManager.isKeyJustPressed(KeyEvent.VK_O) && inputManager.isKeyDown(KeyEvent.VK_CONTROL)) {
            // Cargar nivel (Ctrl+O)
            // Por ahora carga el primer nivel encontrado
            List<String> levels = getLevelNames();
            if (!levels.isEmpty()) {
                loadLevel(levels.get(0));
            } else {
                System.out.println("No hay niveles guardados");
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        // Fondo
        Image bg = assetManager.getSprite("/tilesheet/background");
        if (bg != null) {
            g.drawImage(bg, 0, 0, engine.getWidth(), engine.getHeight(), null);
        }

        for (GridPos gridPos : scenarioGridPositions.keySet()) {
            Image path = assetManager.getSprite(scenarioGridPositions.get(gridPos));
            if (path != null) {
                g.drawImage(path, (int) gridPos.getTopLeft().x, (int) gridPos.getTopLeft().y, Config.TILE_SIZE,
                        Config.TILE_SIZE, null);
            }
        }

        // Waypoints
        for (int index = 0; index < waypoints.size(); index++) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("W" + index, (int) waypoints.get(index).getCentro().x,
                    (int) waypoints.get(index).getCentro().y);
        }

        // UI personalizada
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Scenario", 10, 50);

        // Hotbar
        int hotbarTileSize = 80;
        int hotbarWidth = hotbarTileSize * 9;
        int hotbarHeight = hotbarTileSize;
        int hotbarX = Config.WINDOW_WIDTH / 2 - hotbarWidth / 2;
        int hotbarY = Config.WINDOW_HEIGHT - hotbarHeight - 8;
        g.setColor(Color.GRAY);
        g.fillRect(hotbarX, hotbarY, hotbarWidth, hotbarHeight);

        // Selected tile in the hotbar
        g.setColor(Color.WHITE);
        g.fillRect(hotbarX + hotbarIndex * hotbarTileSize, hotbarY, hotbarTileSize, hotbarTileSize);

        // Tiles in the hotbar
        for (int i = 0; i < 9; i++) {
            g.drawImage(assetManager.getSprite(hotbarTiles[i]), hotbarX + i * hotbarTileSize, hotbarY, hotbarTileSize,
                    hotbarTileSize, null);
        }

        // Entidades
        for (Entity entity : getEntities()) {
            drawEntity(g, entity);
        }
    }

    /**
     * Convierte el estado actual del editor a un objeto Level
     */
    public Level toLevel(String name) {
        Level level = new Level(name);

        // Convertir tiles
        for (Map.Entry<GridPos, String> entry : scenarioGridPositions.entrySet()) {
            GridPos gridPos = entry.getKey();
            String key = gridPos.getX() + "," + gridPos.getY();
            level.getTiles().put(key, new Level.TilePosition(
                    gridPos.getX(),
                    gridPos.getY(),
                    entry.getValue()));
        }

        // Convertir waypoints
        for (GridPos waypoint : waypoints) {
            level.getWaypoints().add(new Level.WaypointPosition(
                    waypoint.getX(),
                    waypoint.getY()));
        }

        return level;
    }

    /**
     * Carga un nivel desde MongoDB y lo aplica al editor
     */
    public void loadLevel(String name) {
        Level level = levelRepository.findByName(name);
        if (level == null) {
            System.out.println("Nivel no encontrado: " + name);
            return;
        }

        // Limpiar estado actual
        scenarioGridPositions.clear();
        waypoints.clear();

        // Cargar tiles
        for (Level.TilePosition tile : level.getTiles().values()) {
            GridPos gridPos = new GridPos(tile.getX(), tile.getY());
            scenarioGridPositions.put(gridPos, tile.getSpritePath());
        }

        // Cargar waypoints
        for (Level.WaypointPosition waypoint : level.getWaypoints()) {
            waypoints.add(new GridPos(waypoint.getX(), waypoint.getY()));
        }

        System.out.println("Nivel cargado: " + name);
    }

    /**
     * Guarda el nivel actual en MongoDB
     */
    public void saveLevel(String name) {
        Level level = toLevel(name);
        levelRepository.saveLevel(level);
        System.out.println("Nivel guardado: " + name);
    }

    /**
     * Obtiene la lista de todos los niveles guardados
     */
    public List<String> getLevelNames() {
        List<String> names = new ArrayList<>();
        for (Level level : levelRepository.findAll()) {
            names.add(level.getName());
        }
        return names;
    }
}
