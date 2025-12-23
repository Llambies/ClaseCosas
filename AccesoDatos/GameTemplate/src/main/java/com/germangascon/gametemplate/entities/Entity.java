package com.germangascon.gametemplate.entities;

import com.germangascon.gametemplate.core.CollisionLayer;
import com.germangascon.gametemplate.core.GameContext;
import com.germangascon.gametemplate.math.Vector2;

/**
 * <p><strong>Entity</strong></p>
 * <p>Descripción</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-12-10<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public abstract class Entity {
    private final Vector2 initialPosition;
    protected final Vector2 position;
    protected final int width;
    protected final int height;
    protected final int hitboxWidth;
    protected final int hitboxHeight;
    protected final int initialHp;
    protected int maxHp;
    protected int hp;
    protected final int initialDamage;
    protected int damage;
    private final String sprite;
    private boolean alive;
    // Capa en la que está la Entity
    private int collisionLayer;
    // Capas con las que queremos que colisione
    private int collisionMask;
    // GameContext Interfaz para que la Entity pueda consultar e interactuar con el Engine de forma controlada y limitada
    protected GameContext gameContext;

    public Entity(float x, float y, int width, int height, int hitboxWidth, int hitboxHeight, int hp, int damage, String sprite) {
        this.initialPosition = new Vector2(x, y);
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.initialHp = hp;
        this.maxHp = hp;
        this.hp = hp;
        this.initialDamage = damage;
        this.damage = damage;
        this.sprite = sprite;
        this.alive = true;
        // Por defecto asumimos que la entidad no está en ninguna capa (colisionLayer = 0)
        this.collisionLayer = CollisionLayer.LAYER_NONE;
        // Por defecto no colisiona con nada. Hay que tener en cuenta que al estar en la capa 0 no colisionaría con nada tampoco
        this.collisionMask = CollisionLayer.LAYER_NONE;
    }

    public Vector2 getInitialPosition() {
        return initialPosition;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getHitboxWidth() {
        return hitboxWidth;
    }

    public int getHitboxHeight() {
        return hitboxHeight;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getSprite() {
        return sprite;
    }

    public Vector2 getDrawPosition() {
        return new Vector2(position.x - width / 2f, position.y - height / 2f);
    }

    /**
     * Obtiene el límite físico izquierdo de la Entity basado en la hitbox
     * @return Límite físico izquierdo
     */
    public float getLeft() {
        return position.x -  hitboxWidth / 2f;
    }

    /**
     * Obtiene el límite físico derecho de la Entity basado en la hitbox
     * @return Límite físico derecho
     */
    public float getRight() {
        return position.x + hitboxWidth / 2f;
    }

    /**
     * Obtiene el límite físico superior de la Entity basado en la hitbox
     * @return Límite físico superior
     */
    public float getTop() {
        return position.y - hitboxHeight / 2f;
    }

    /**
     * Obtiene el límite físico inferior de la Entity basado en la hitbox
     * @return Límite físico inferior
     */
    public float getBottom() {
        return position.y + hitboxHeight / 2f;
    }

    public boolean isAlive() {
        return alive;
    }

    public void destroy() {
        alive = false;
    }

    public int getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(int collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public int getCollisionMask() {
        return collisionMask;
    }

    /**
     * Añade una nueva capa con la que esta Entity puede colisionar
     * @param collisionMask Capa con la que quiere colisionar
     */
    public void addCollisionMask(int collisionMask) {
        this.collisionMask |= collisionMask;
    }

    /**
     * Devuelve true si, a nivel de "filtros", esta entidad
     * está interesada en colisionar con la otra.
     * @param other La otra Entitiy a comprobar si puede existir colisión
     */
    public boolean canCollideWith(Entity other) {
        // Regla estándar: la capa del colisiona con mi máscara de colisión
        return (other.collisionLayer & this.collisionMask) != 0;
    }

    /**
     * La Entity ha colisionado con la Entity recibida como parámetro
     * @param entity Entity con la que ha colisionado
     */
    public abstract void onCollision(Entity entity);

    /**
     * Recibe daño de la Entity recibida como parámetro
     * @param from Entity desde la que se recibe el daño
     */
    public void takeDamage(Entity from) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            // onDeath(source);
            destroy();
        }
    }

    /**
     * Reinicia la Entity a los parámetros iniciales: position, hp y damage
     */
    public void reset() {
        position.set(initialPosition);
        hp = initialHp;
        damage = initialDamage;
    }

    /**
     * Método para actualizar los parámetros físicos de la Entity
     * @param deltaTime Cantidad de tiempo, medida en segundos, que ha transcurrido
     * desde el último frame. Útil para una ejecución fluida e independiente
     * de la velocidad del hardware
     */
    public abstract void update(double deltaTime);

    /**
     * Método que se ejecuta justo antes del update de la Entity
     * Útil para tareas de inicialización
     * @param deltaTime tiempo en segundos desde el último update.
     */
    public void preUpdate(double deltaTime) {

    }

    /**
     * Método que se ejecuta justo después del update de la Entity
     * Útil para tareas de finalización
     * @param deltaTime tiempo en segundos desde el último update.
     */
    public void postUpdate(double deltaTime) {

    }

    /**
     * Método para actualizar los parámetros físicos de la Entity.
     * Este método está asegurado que se ejecutará después de haber ejecutado
     * todos los updates de todas las Entities
     * @param deltaTime Cantidad de tiempo, medida en segundos, que ha transcurrido
     * desde el último frame. Útil para una ejecución fluida e independiente
     * de la velocidad del hardware
     */
    public void lateUpdate(double deltaTime) {

    }

    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "initialPosition=" + initialPosition +
                ", position=" + position +
                ", width=" + width +
                ", height=" + height +
                ", hitboxWidth=" + hitboxWidth +
                ", hitboxHeight=" + hitboxHeight +
                ", initialHp=" + initialHp +
                ", maxHp=" + maxHp +
                ", hp=" + hp +
                ", initialDamage=" + initialDamage +
                ", damage=" + damage +
                ", sprite='" + sprite + '\'' +
                ", alive=" + alive +
                ", collisionLayer=" + collisionLayer +
                ", collisionMask=" + collisionMask +
                '}';
    }
}
