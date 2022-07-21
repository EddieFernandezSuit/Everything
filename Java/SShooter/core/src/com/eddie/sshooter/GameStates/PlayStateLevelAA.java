package com.eddie.sshooter.GameStates;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.eddie.sshooter.Entities.Bullet;
import com.eddie.sshooter.Entities.Particle;
import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Entities.Upgrade;
import com.eddie.sshooter.Main.MainGame;
import com.eddie.sshooter.Managers.GameKeys;
import com.eddie.sshooter.Managers.GameStateManager;

import java.util.ArrayList;

public class PlayStateLevelAA extends PlayState{

    public PlayStateLevelAA(GameStateManager gsm){
        super(gsm);
    }


    public void init() {

        super.init();

        ebTime = 1;
        efTime = 10000;
        ebigTime = 10000;
        enbuTime = 10000;
        level = 1;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.TWO );
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
