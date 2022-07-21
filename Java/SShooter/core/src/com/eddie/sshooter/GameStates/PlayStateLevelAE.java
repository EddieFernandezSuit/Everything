package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelAE extends PlayState{

    public PlayStateLevelAE(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();

        ebTime = .1f;
        efTime = 1000;
        ebigTime = 1000;
        level = 5;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.SIX);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
