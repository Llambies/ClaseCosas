package com.germangascon.gametemplate;

import com.germangascon.gametemplate.core.Config;
import com.germangascon.gametemplate.core.GameLauncher;
import com.germangascon.gametemplate.game.scenes.ExampleScene;
import com.germangascon.gametemplate.game.scenes.EditorScene;

/**
 * <p><strong>BasicGameTemplate</strong></p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-10<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class Main {
    public static void main(String[] args) {
        example();
    }

    public static void editor() {
        EditorScene editorScene = new EditorScene();
        new GameLauncher("Editor", editorScene, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT,
                Config.TILE_SIZE, Config.TARGET_FPS, Config.TARGET_UPDATES);
    }
    
    public static void example() {
        ExampleScene exampleScene = new ExampleScene();
        new GameLauncher("Game Template", exampleScene, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT,
                Config.TILE_SIZE, Config.TARGET_FPS, Config.TARGET_UPDATES);
    }
}