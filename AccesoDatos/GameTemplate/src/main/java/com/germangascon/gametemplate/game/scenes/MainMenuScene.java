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
    private int hoveredLevelIndex; // -1 si no hay hover
    private int hoveredMenuOption; // -1 si no hay hover, 0 = Nueva Partida, 1 = Cargar Partida
    private boolean hoveredEditorButton; // true si el mouse está sobre el botón del editor

    public MainMenuScene() {
        this.levelRepository = new LevelRepository();
        this.gameSaveManager = new GameSaveManager();
        this.availableLevels = new ArrayList<>();
        this.selectedLevelIndex = 0;
        this.selectedMenuOption = 0;
        this.showLoadGameOption = false;
        this.hoveredLevelIndex = -1;
        this.hoveredMenuOption = -1;
        this.hoveredEditorButton = false;
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
        // Detectar hover del mouse
        handleMouseHover();
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
            inputManager.consumeLeftClick();
        }
    }

    private void handleMouseHover() {
        int mouseX = inputManager.getMouseX();
        int mouseY = inputManager.getMouseY();
        
        // Verificar hover sobre botón del editor
        int editorButtonX = engine.getWidth() / 2 - 100;
        int editorButtonY = engine.getHeight() - 80;
        int editorButtonWidth = 200;
        int editorButtonHeight = 40;
        
        hoveredEditorButton = (mouseX >= editorButtonX && mouseX <= editorButtonX + editorButtonWidth &&
                              mouseY >= editorButtonY && mouseY <= editorButtonY + editorButtonHeight);
        
        if (availableLevels.isEmpty()) {
            hoveredLevelIndex = -1;
            hoveredMenuOption = -1;
            return;
        }

        int startY = 200;
        int levelButtonHeight = 50;
        int levelButtonSpacing = 10;
        int buttonX = 100;
        int buttonWidth = 500;
        
        hoveredLevelIndex = -1;
        hoveredMenuOption = -1;

        // Verificar hover sobre niveles (solo para feedback visual)
        for (int i = 0; i < availableLevels.size(); i++) {
            int buttonY = startY + i * (levelButtonHeight + levelButtonSpacing);
            if (mouseX >= buttonX && mouseX <= buttonX + buttonWidth &&
                mouseY >= buttonY && mouseY <= buttonY + levelButtonHeight) {
                hoveredLevelIndex = i;
                
                // Verificar hover sobre botones de acción (solo si el nivel está seleccionado)
                if (i == selectedLevelIndex) {
                    int actionButtonY = buttonY + levelButtonHeight + 5;
                    int newGameButtonX = buttonX;
                    int loadGameButtonX = buttonX + 200;
                    int actionButtonWidth = 180;
                    int actionButtonHeight = 35;

                    if (mouseY >= actionButtonY && mouseY <= actionButtonY + actionButtonHeight) {
                        if (mouseX >= newGameButtonX && mouseX <= newGameButtonX + actionButtonWidth) {
                            hoveredMenuOption = 0;
                        } else if (showLoadGameOption && 
                                   mouseX >= loadGameButtonX && mouseX <= loadGameButtonX + actionButtonWidth) {
                            hoveredMenuOption = 1;
                        }
                    }
                }
                return;
            }
        }
    }

    private void handleMouseClick(int mouseX, int mouseY) {
        // Verificar clic en botón del editor
        int editorButtonX = engine.getWidth() / 2 - 100;
        int editorButtonY = engine.getHeight() - 80;
        int editorButtonWidth = 200;
        int editorButtonHeight = 40;
        
        if (mouseX >= editorButtonX && mouseX <= editorButtonX + editorButtonWidth &&
            mouseY >= editorButtonY && mouseY <= editorButtonY + editorButtonHeight) {
            openEditor();
            return;
        }
        
        if (availableLevels.isEmpty()) {
            return;
        }

        int startY = 200;
        int levelButtonHeight = 50;
        int levelButtonSpacing = 10;
        int buttonX = 100;
        int buttonWidth = 500;

        // Primero verificar si se hace clic en los botones de acción del nivel seleccionado
        if (selectedLevelIndex >= 0 && selectedLevelIndex < availableLevels.size()) {
            int selectedButtonY = startY + selectedLevelIndex * (levelButtonHeight + levelButtonSpacing);
            int actionButtonY = selectedButtonY + levelButtonHeight + 5;
            int newGameButtonX = buttonX;
            int loadGameButtonX = buttonX + 200;
            int actionButtonWidth = 180;
            int actionButtonHeight = 35;

            // Verificar clic en botones de acción
            if (mouseY >= actionButtonY && mouseY <= actionButtonY + actionButtonHeight) {
                if (mouseX >= newGameButtonX && mouseX <= newGameButtonX + actionButtonWidth) {
                    startNewGame(availableLevels.get(selectedLevelIndex).getName());
                    return;
                } else if (showLoadGameOption && 
                           mouseX >= loadGameButtonX && mouseX <= loadGameButtonX + actionButtonWidth) {
                    loadGame(availableLevels.get(selectedLevelIndex).getName());
                    return;
                }
            }
        }

        // Verificar clic en niveles (para seleccionar)
        for (int i = 0; i < availableLevels.size(); i++) {
            int buttonY = startY + i * (levelButtonHeight + levelButtonSpacing);
            if (mouseX >= buttonX && mouseX <= buttonX + buttonWidth &&
                mouseY >= buttonY && mouseY <= buttonY + levelButtonHeight) {
                // Seleccionar el nivel
                selectedLevelIndex = i;
                selectedMenuOption = 0; // Resetear la opción de menú
                
                // Verificar si hay partida guardada para este nivel
                GameSave save = gameSaveManager.findByLevelName(availableLevels.get(i).getName());
                showLoadGameOption = (save != null);
                currentSave = save;
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

    private void openEditor() {
        EditorScene editorScene = new EditorScene();
        engine.setScene(editorScene);
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
            boolean isHovered = (i == hoveredLevelIndex);

            // Fondo del botón
            if (isSelected) {
                g.setColor(new Color(100, 150, 255, 200));
            } else if (isHovered) {
                g.setColor(new Color(80, 120, 200, 200));
            } else {
                g.setColor(new Color(50, 50, 70, 200));
            }
            g.fillRect(buttonX, buttonY, buttonWidth, levelButtonHeight);

            // Borde
            if (isSelected) {
                g.setColor(Color.WHITE);
                g.setStroke(new BasicStroke(3));
            } else if (isHovered) {
                g.setColor(new Color(200, 200, 255));
                g.setStroke(new BasicStroke(2));
            } else {
                g.setColor(Color.GRAY);
                g.setStroke(new BasicStroke(1));
            }
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
                boolean isNewGameHovered = (hoveredMenuOption == 0);
                boolean isNewGameSelected = (selectedMenuOption == 0);
                if (isNewGameSelected || isNewGameHovered) {
                    g.setColor(isNewGameSelected ? new Color(0, 180, 0, 220) : new Color(0, 150, 0, 200));
                } else {
                    g.setColor(new Color(0, 100, 0, 150));
                }
                g.fillRect(newGameButtonX, actionButtonY, actionButtonWidth, actionButtonHeight);
                g.setColor((isNewGameSelected || isNewGameHovered) ? Color.WHITE : Color.LIGHT_GRAY);
                g.setStroke(new BasicStroke((isNewGameSelected || isNewGameHovered) ? 2 : 1));
                g.drawRect(newGameButtonX, actionButtonY, actionButtonWidth, actionButtonHeight);
                g.setFont(new Font("Rubik", Font.BOLD, 16));
                FontMetrics buttonMetrics = g.getFontMetrics();
                String newGameText = "Nueva Partida";
                int textX = newGameButtonX + (actionButtonWidth - buttonMetrics.stringWidth(newGameText)) / 2;
                int textY = actionButtonY + (actionButtonHeight + buttonMetrics.getAscent() - buttonMetrics.getDescent()) / 2;
                g.drawString(newGameText, textX, textY);

                // Botón Cargar Partida (solo si hay partida guardada)
                if (showLoadGameOption) {
                    boolean isLoadGameHovered = (hoveredMenuOption == 1);
                    boolean isLoadGameSelected = (selectedMenuOption == 1);
                    if (isLoadGameSelected || isLoadGameHovered) {
                        g.setColor(isLoadGameSelected ? new Color(0, 130, 230, 220) : new Color(0, 100, 200, 200));
                    } else {
                        g.setColor(new Color(0, 70, 150, 150));
                    }
                    g.fillRect(loadGameButtonX, actionButtonY, actionButtonWidth, actionButtonHeight);
                    g.setColor((isLoadGameSelected || isLoadGameHovered) ? Color.WHITE : Color.LIGHT_GRAY);
                    g.setStroke(new BasicStroke((isLoadGameSelected || isLoadGameHovered) ? 2 : 1));
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

        // Botón del Editor
        int editorButtonX = engine.getWidth() / 2 - 100;
        int editorButtonY = engine.getHeight() - 80;
        int editorButtonWidth = 200;
        int editorButtonHeight = 40;
        
        // Fondo del botón
        if (hoveredEditorButton) {
            g.setColor(new Color(150, 100, 200, 220));
        } else {
            g.setColor(new Color(100, 70, 150, 200));
        }
        g.fillRect(editorButtonX, editorButtonY, editorButtonWidth, editorButtonHeight);
        
        // Borde del botón
        g.setColor(hoveredEditorButton ? Color.WHITE : Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(hoveredEditorButton ? 2 : 1));
        g.drawRect(editorButtonX, editorButtonY, editorButtonWidth, editorButtonHeight);
        
        // Texto del botón
        g.setFont(new Font("Rubik", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        String editorText = "Editor de Niveles";
        FontMetrics editorMetrics = g.getFontMetrics();
        int editorTextX = editorButtonX + (editorButtonWidth - editorMetrics.stringWidth(editorText)) / 2;
        int editorTextY = editorButtonY + (editorButtonHeight + editorMetrics.getAscent() - editorMetrics.getDescent()) / 2;
        g.drawString(editorText, editorTextX, editorTextY);

        // Instrucciones
        g.setFont(new Font("Rubik", Font.PLAIN, 14));
        g.setColor(Color.GRAY);
        String instructions = "Click en nivel para seleccionar | Click en botón para iniciar | Flechas/Enter: Teclado | ESC: Salir";
        FontMetrics instMetrics = g.getFontMetrics();
        int instX = (engine.getWidth() - instMetrics.stringWidth(instructions)) / 2;
        g.drawString(instructions, instX, engine.getHeight() - 30);
    }

    @Override
    public com.germangascon.gametemplate.game.EntityFactory getEntityFactory() {
        return null; // El menú no necesita EntityFactory
    }
}

