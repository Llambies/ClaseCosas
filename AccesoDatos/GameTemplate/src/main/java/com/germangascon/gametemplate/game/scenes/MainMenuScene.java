package com.germangascon.gametemplate.game.scenes;

import com.germangascon.gametemplate.core.GameScene;
import com.germangascon.gametemplate.game.Level;
import com.germangascon.gametemplate.game.LevelRepository;
import com.germangascon.gametemplate.game.GameSave;
import com.germangascon.gametemplate.game.GameSaveManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * <p><strong>MainMenuScene</strong></p>
 * <p>Escena del menú principal con selector de nivel</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-15<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class MainMenuScene extends GameScene {
    private LevelRepository levelRepository;
    private GameSaveManager gameSaveManager;
    private List<Level> availableLevels;
    private int selectedLevelIndex;
    private int selectedMenuOption; // 0 = Nueva Partida, 1 = Cargar Partida
    private boolean showLoadGameOption;
    private GameSave currentSave;

    public MainMenuScene() {
        this.levelRepository = new LevelRepository();
        this.gameSaveManager = new GameSaveManager();
        this.availableLevels = new ArrayList<>();
        this.selectedLevelIndex = 0;
        this.selectedMenuOption = 0;
        this.showLoadGameOption = false;
        loadAvailableLevels();
    }

    private void loadAvailableLevels() {
        availableLevels = levelRepository.findAll();
        if (availableLevels.isEmpty()) {
            // Si no hay niveles, crear uno por defecto
            System.out.println("No se encontraron niveles. Usando nivel por defecto.");
        }
    }

    @Override
    public void loadAssets() {
        // No necesitamos assets para el menú
    }

    @Override
    protected void init(com.germangascon.gametemplate.core.Engine engine) {
        super.init(engine);
    }

    @Override
    protected void update(double deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void processInput() {
        // Navegación con teclado
        if (inputManager.isKeyJustPressed(KeyEvent.VK_UP)) {
            if (selectedMenuOption == 0) {
                // Navegar por niveles
                selectedLevelIndex = (selectedLevelIndex - 1 + availableLevels.size()) % availableLevels.size();
            } else {
                selectedMenuOption = 0;
            }
        }

        if (inputManager.isKeyJustPressed(KeyEvent.VK_DOWN)) {
            if (selectedMenuOption == 0) {
                // Navegar por niveles
                selectedLevelIndex = (selectedLevelIndex + 1) % availableLevels.size();
            } else {
                selectedMenuOption = 0;
            }
        }

        if (inputManager.isKeyJustPressed(KeyEvent.VK_ENTER) || inputManager.isKeyJustPressed(KeyEvent.VK_SPACE)) {
            if (availableLevels.isEmpty()) {
                return;
            }

            Level selectedLevel = availableLevels.get(selectedLevelIndex);
            
            if (selectedMenuOption == 0) {
                // Nueva partida
                startNewGame(selectedLevel.getName());
            } else if (selectedMenuOption == 1) {
                // Cargar partida
                loadGame(selectedLevel.getName());
            }
        }

        // Navegación entre opciones de menú (Nueva Partida / Cargar Partida)
        if (inputManager.isKeyJustPressed(KeyEvent.VK_LEFT)) {
            selectedMenuOption = 0;
        }
        if (inputManager.isKeyJustPressed(KeyEvent.VK_RIGHT)) {
            if (showLoadGameOption) {
                selectedMenuOption = 1;
            }
        }

        // Click del ratón
        if (inputManager.hasLeftClick()) {
            int mouseX = inputManager.getMouseX();
            int mouseY = inputManager.getMouseY();
            handleMouseClick(mouseX, mouseY);
        }
    }

    private void handleMouseClick(int mouseX, int mouseY) {
        if (availableLevels.isEmpty()) {
            return;
        }

        int startY = 200;
        int levelButtonHeight = 50;
        int levelButtonSpacing = 10;

        // Verificar clic en niveles
        for (int i = 0; i < availableLevels.size(); i++) {
            int buttonY = startY + i * (levelButtonHeight + levelButtonSpacing);
            if (mouseX >= 100 && mouseX <= 600 &&
                mouseY >= buttonY && mouseY <= buttonY + levelButtonHeight) {
                selectedLevelIndex = i;
                
                // Verificar si hay partida guardada para este nivel
                GameSave save = gameSaveManager.findByLevelName(availableLevels.get(i).getName());
                showLoadGameOption = (save != null);
                currentSave = save;
                
                // Verificar clic en botones de acción
                int actionButtonY = buttonY + levelButtonHeight + 5;
                int newGameButtonX = 100;
                int loadGameButtonX = 300;
                int buttonWidth = 180;
                int buttonHeight = 35;

                if (mouseY >= actionButtonY && mouseY <= actionButtonY + buttonHeight) {
                    if (mouseX >= newGameButtonX && mouseX <= newGameButtonX + buttonWidth) {
                        startNewGame(availableLevels.get(i).getName());
                    } else if (showLoadGameOption && 
                               mouseX >= loadGameButtonX && mouseX <= loadGameButtonX + buttonWidth) {
                        loadGame(availableLevels.get(i).getName());
                    }
                }
                return;
            }
        }
    }

    private void startNewGame(String levelName) {
        ExampleScene gameScene = new ExampleScene();
        gameScene.loadLevel(levelName);
        engine.setScene(gameScene);
    }

    private void loadGame(String levelName) {
        GameSave save = gameSaveManager.findByLevelName(levelName);
        if (save == null) {
            System.out.println("No hay partida guardada para el nivel: " + levelName);
            return;
        }

        ExampleScene gameScene = new ExampleScene();
        gameScene.loadLevel(levelName);
        gameScene.loadGameState(save);
        engine.setScene(gameScene);
    }

    @Override
    public void draw(Graphics2D g) {
        // Fondo
        g.setColor(new Color(20, 20, 30));
        g.fillRect(0, 0, engine.getWidth(), engine.getHeight());

        // Título
        g.setColor(Color.WHITE);
        g.setFont(new Font("Rubik", Font.BOLD, 48));
        FontMetrics titleMetrics = g.getFontMetrics();
        String title = "TOWER DEFENSE";
        int titleX = (engine.getWidth() - titleMetrics.stringWidth(title)) / 2;
        g.drawString(title, titleX, 100);

        // Subtítulo
        g.setFont(new Font("Rubik", Font.PLAIN, 24));
        FontMetrics subtitleMetrics = g.getFontMetrics();
        String subtitle = "Selecciona un Nivel";
        int subtitleX = (engine.getWidth() - subtitleMetrics.stringWidth(subtitle)) / 2;
        g.drawString(subtitle, subtitleX, 150);

        if (availableLevels.isEmpty()) {
            g.setFont(new Font("Rubik", Font.PLAIN, 18));
            g.setColor(Color.YELLOW);
            String noLevels = "No hay niveles disponibles";
            FontMetrics noLevelsMetrics = g.getFontMetrics();
            int noLevelsX = (engine.getWidth() - noLevelsMetrics.stringWidth(noLevels)) / 2;
            g.drawString(noLevels, noLevelsX, 300);
            return;
        }

        // Lista de niveles
        int startY = 200;
        int levelButtonHeight = 50;
        int levelButtonSpacing = 10;
        int buttonX = 100;
        int buttonWidth = 500;

        for (int i = 0; i < availableLevels.size(); i++) {
            Level level = availableLevels.get(i);
            int buttonY = startY + i * (levelButtonHeight + levelButtonSpacing);
            boolean isSelected = (i == selectedLevelIndex);

            // Fondo del botón
            if (isSelected) {
                g.setColor(new Color(100, 150, 255, 200));
            } else {
                g.setColor(new Color(50, 50, 70, 200));
            }
            g.fillRect(buttonX, buttonY, buttonWidth, levelButtonHeight);

            // Borde
            g.setColor(isSelected ? Color.WHITE : Color.GRAY);
            g.setStroke(new BasicStroke(isSelected ? 3 : 1));
            g.drawRect(buttonX, buttonY, buttonWidth, levelButtonHeight);

            // Texto del nivel
            g.setFont(new Font("Rubik", Font.BOLD, 20));
            g.setColor(Color.WHITE);
            g.drawString(level.getName(), buttonX + 20, buttonY + 35);

            // Botones de acción (solo para el nivel seleccionado)
            if (isSelected) {
                int actionButtonY = buttonY + levelButtonHeight + 5;
                int newGameButtonX = buttonX;
                int loadGameButtonX = buttonX + 200;
                int actionButtonWidth = 180;
                int actionButtonHeight = 35;

                // Verificar si hay partida guardada
                GameSave save = gameSaveManager.findByLevelName(level.getName());
                showLoadGameOption = (save != null);
                currentSave = save;

                // Botón Nueva Partida
                g.setColor(selectedMenuOption == 0 ? new Color(0, 150, 0, 200) : new Color(0, 100, 0, 150));
                g.fillRect(newGameButtonX, actionButtonY, actionButtonWidth, actionButtonHeight);
                g.setColor(selectedMenuOption == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                g.setStroke(new BasicStroke(selectedMenuOption == 0 ? 2 : 1));
                g.drawRect(newGameButtonX, actionButtonY, actionButtonWidth, actionButtonHeight);
                g.setFont(new Font("Rubik", Font.BOLD, 16));
                FontMetrics buttonMetrics = g.getFontMetrics();
                String newGameText = "Nueva Partida";
                int textX = newGameButtonX + (actionButtonWidth - buttonMetrics.stringWidth(newGameText)) / 2;
                int textY = actionButtonY + (actionButtonHeight + buttonMetrics.getAscent() - buttonMetrics.getDescent()) / 2;
                g.drawString(newGameText, textX, textY);

                // Botón Cargar Partida (solo si hay partida guardada)
                if (showLoadGameOption) {
                    g.setColor(selectedMenuOption == 1 ? new Color(0, 100, 200, 200) : new Color(0, 70, 150, 150));
                    g.fillRect(loadGameButtonX, actionButtonY, actionButtonWidth, actionButtonHeight);
                    g.setColor(selectedMenuOption == 1 ? Color.WHITE : Color.LIGHT_GRAY);
                    g.setStroke(new BasicStroke(selectedMenuOption == 1 ? 2 : 1));
                    g.drawRect(loadGameButtonX, actionButtonY, actionButtonWidth, actionButtonHeight);
                    g.setFont(new Font("Rubik", Font.BOLD, 16));
                    String loadGameText = "Cargar Partida";
                    textX = loadGameButtonX + (actionButtonWidth - buttonMetrics.stringWidth(loadGameText)) / 2;
                    g.drawString(loadGameText, textX, textY);

                    // Mostrar información de la partida guardada
                    if (currentSave != null) {
                        g.setFont(new Font("Rubik", Font.PLAIN, 12));
                        g.setColor(Color.YELLOW);
                        String saveInfo = String.format("Oleada: %d | Dinero: %d | Torretas: %d", 
                            currentSave.getCurrentWave(), 
                            currentSave.getMoney(),
                            currentSave.getTowers().size());
                        g.drawString(saveInfo, buttonX + 20, actionButtonY + actionButtonHeight + 20);
                    }
                }
            }
        }

        // Instrucciones
        g.setFont(new Font("Rubik", Font.PLAIN, 14));
        g.setColor(Color.GRAY);
        String instructions = "Flechas: Navegar | Enter: Seleccionar | ESC: Salir";
        FontMetrics instMetrics = g.getFontMetrics();
        int instX = (engine.getWidth() - instMetrics.stringWidth(instructions)) / 2;
        g.drawString(instructions, instX, engine.getHeight() - 30);
    }

    @Override
    public com.germangascon.gametemplate.game.EntityFactory getEntityFactory() {
        return null; // El menú no necesita EntityFactory
    }
}

