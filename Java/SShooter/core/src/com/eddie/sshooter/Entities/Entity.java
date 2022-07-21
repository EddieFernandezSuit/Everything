package com.eddie.sshooter.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
    protected float x;
    protected float y;
    protected float dx;
    protected float dy;
    protected float scaledSpeed;
    protected float speed;
    protected static float classSpeed;
    protected int width;
    protected int height;
    protected int scaledSize;
    protected boolean remove;

    public float getx(){ return x; }
    public float gety(){ return y; }

    //public void setSpeed(int scalar){ speed *= scalar; }
    //public void setSize(int scalar){ speed *= scalar; }

    public static float getSpeed(){ return classSpeed; }

    public boolean shouldRemove(){ return remove; }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle( x, y, width * scaledSize, height * scaledSize);
    }

    public void draw(SpriteBatch sb, Texture img){
        sb.draw(img, x, y, width * scaledSize, height* scaledSize, 0, 0, width, height, false, false);
    }
}
