package com.neet.entities;

import com.neet.eddie.playground.PlaygroundGame;

public class SpaceObject {
    protected float x;
    protected float y;
    protected float dx;
    protected float dy;
    protected float radians;
    protected float speed;
    protected float rotationSpeed;
    protected int width;
    protected int height;
    protected float[] shapex;
    protected float[] shapey;

    public float getx() {
        return x;
    }
    public float gety() {
        return y;
    }

    public float[] getShapex() {
        return shapex;
    }
    public float[] getShapey() {
        return shapey;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean intersects(SpaceObject other) {
        float[] sx = other.getShapex();
        float[] sy = other.getShapey();
        for (int i = 0; i < sx.length; i++) {
            if (contains(sx[i], sy[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a point is in the object.
     * @param x X-coordinate to check
     * @param y Y-coordinate to check
     * @return True if the point is in the object.
     */
    public boolean contains(float x, float y) {
        boolean b = false;
        for (int i = 0, j = shapex.length - 1;
             i < shapex.length;
             j = i++) {
            if ((shapey[i] > y) != (shapey[j] > y) &&
                    (x < (shapex[j] - shapex[i]) * (y - shapey[i]) / (shapey[j] - shapey[i]) + shapex[i])) {
                b = !b;
            }
        } // {0} [1] 2 3 4
        return b;
    }


    protected void wrap() {
        if (x < 0) x = PlaygroundGame.WIDTH;
        if (x > PlaygroundGame.WIDTH) x = 0;
        if (y < 0) y = PlaygroundGame.HEIGHT;
        if (y > PlaygroundGame.HEIGHT) y = 0;

    }
}
