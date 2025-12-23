package com.germangascon.gametemplate.core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

public class AssetManager {
    private final Map<String, BufferedImage> sprites;

    public AssetManager() {
        sprites = new HashMap<>();
    }

    public void loadSprite(String spritePath) throws IOException {
        BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(spritePath)));
        sprites.put(spritePath, image);
    }

    public boolean containsSprite(String spriteName) {
        return sprites.containsKey(spriteName);
    }

    public BufferedImage getSprite(String spriteName) {
        return sprites.get(spriteName);
    }

    /**
     * Carga un tilesheet y extrae un sprite específico basado en coordenadas de
     * tile
     * 
     * @param tilesheetPath Ruta del tilesheet (ej: "/tilesheet/colored_packed.png")
     * @param spriteKey     Clave única para identificar este sprite (ej:
     *                      "tank_red")
     * @param tileX         Coordenada X del tile en el tilesheet (índice, no
     *                      píxeles)
     * @param tileY         Coordenada Y del tile en el tilesheet (índice, no
     *                      píxeles)
     * @param tileWidth     Ancho del tile en píxeles
     * @param tileHeight    Alto del tile en píxeles
     * @param rotation      Rotación en grados (0 = sin rotación, positivo =
     *                      antihorario)
     * @throws IOException Si hay error cargando la imagen
     */
    public void loadSpriteFromTilesheet(String tilesheetPath, String spriteKey,
            int tileX, int tileY,
            int tileWidth, int tileHeight, double rotation) throws IOException {
        // Cargar el tilesheet si no está cargado
        if (!sprites.containsKey(tilesheetPath)) {
            BufferedImage tilesheet = ImageIO.read(
                    Objects.requireNonNull(getClass().getResourceAsStream(tilesheetPath)));
            sprites.put(tilesheetPath, tilesheet);
        }

        // Obtener el tilesheet
        BufferedImage tilesheet = sprites.get(tilesheetPath);

        // Calcular coordenadas en píxeles
        int x = tileX * tileWidth;
        int y = tileY * tileHeight;

        // Extraer el sprite del tilesheet
        BufferedImage sprite = tilesheet.getSubimage(x, y, tileWidth, tileHeight);

        // Aplicar rotación si es necesaria
        if (rotation != 0) {
            sprite = rotateImage(sprite, rotation);
        }

        // Guardar el sprite extraído con su clave única
        sprites.put(spriteKey, sprite);
    }

    // Sobrecarga sin rotación para mantener compatibilidad
    public void loadSpriteFromTilesheet(String tilesheetPath, String spriteKey,
            int tileX, int tileY,
            int tileWidth, int tileHeight) throws IOException {
        loadSpriteFromTilesheet(tilesheetPath, spriteKey, tileX, tileY, tileWidth, tileHeight, 0);
    }

    /**
     * Carga múltiples sprites de un tilesheet usando un array de coordenadas
     * 
     * @param tilesheetPath Ruta del tilesheet
     * @param tileWidth     Ancho del tile en píxeles
     * @param tileHeight    Alto del tile en píxeles
     * @param spriteData    Array de objetos con {key, tileX, tileY} o {key, tileX,
     *                      tileY, rotation}
     *                      Si rotation no se proporciona, se asume 0
     */
    public void loadSpritesFromTilesheet(String tilesheetPath, int tileWidth, int tileHeight,
            String[][] spriteData) throws IOException {
        for (String[] data : spriteData) {
            String key = data[0];
            int tileX = Integer.parseInt(data[1]);
            int tileY = Integer.parseInt(data[2]);
            double rotation = data.length > 3 ? Double.parseDouble(data[3]) : 0.0;
            loadSpriteFromTilesheet(tilesheetPath, key, tileX, tileY, tileWidth, tileHeight, rotation);
        }
    }

    /**
     * Rota una imagen un número de grados
     * 
     * @param image   Imagen original
     * @param degrees Grados de rotación (positivo = sentido antihorario)
     * @return Nueva imagen rotada
     */
    private BufferedImage rotateImage(BufferedImage image, double degrees) {
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));

        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();

        // Configurar calidad de renderizado
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Trasladar al centro y rotar
        AffineTransform transform = new AffineTransform();
        transform.translate(newWidth / 2.0, newHeight / 2.0);
        transform.rotate(radians);
        transform.translate(-image.getWidth() / 2.0, -image.getHeight() / 2.0);

        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotated;
    }
}
