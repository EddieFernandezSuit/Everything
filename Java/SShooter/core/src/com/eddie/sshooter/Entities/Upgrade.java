package com.eddie.sshooter.Entities;

import com.eddie.sshooter.Main.MainGame;

import static com.badlogic.gdx.math.MathUtils.random;

public class Upgrade extends Entity {

    public int type;
    public static final int BULLETSPEED = 0;
    public static final int BULLETSIZE = 1;
    public static final int BULLETRELOAD = 2;

    public Upgrade(int type){
        this.type = type;

        x = random(0, MainGame.WIDTH);
        y = MainGame.HEIGHT;

        speed = dy = -150;
        width = 16;
        height = 16;
        scaledSize = 1;
    }

    public void update(float dt){
        y += dy * dt;
        if ( y < 0 ){
            remove = true;
        }
    }
}
