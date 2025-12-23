package com.germangascon.gametemplate.math;

import java.util.Objects;

import com.germangascon.gametemplate.core.Config;

public class GridPos {
    private int x;
    private int y;
    private Vector2 centro;

    public GridPos(int x, int y) {
        this.x = x;
        this.y = y;
        recalcularCentro();
    }

    public GridPos(Vector2 vector2) {
        this.x = (int) (vector2.x / Config.TILE_SIZE);
        this.y = (int) (vector2.y / Config.TILE_SIZE);
        recalcularCentro();
    }

    void recalcularCentro() {
        this.centro = new Vector2(x * Config.TILE_SIZE + Config.TILE_SIZE / 2,
                y * Config.TILE_SIZE + Config.TILE_SIZE / 2);
    }

    public Vector2 getCentro() {
        return centro;
    }

    public Vector2 getTopLeft() {
        return new Vector2(x * Config.TILE_SIZE, y * Config.TILE_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GridPos) {
            GridPos other = (GridPos) obj;
            return x == other.x && y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
