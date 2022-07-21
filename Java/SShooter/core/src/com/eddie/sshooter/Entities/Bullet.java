package com.eddie.sshooter.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Bullet extends Entity {
    private float lifeTime;
    private float lifeTimer;

    public Bullet(float x, float y, int speedPowerUp, int sizePowerUp){
        width = height = 16 ;
        scaledSize = sizePowerUp;
        this.x = x + 16 -  8 * scaledSize;
        this.y = y + 16 - 8 * scaledSize;

        speed = 400 * speedPowerUp; //350;
        dx = speed;


        lifeTimer=0;
        lifeTime=1;
    }

    public void update(float dt){
        x += dx*dt;

        lifeTimer += dt;
        if(lifeTimer >= lifeTime){
            remove = true;
        }
    }
}
