package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelAB extends PlayState{

    public PlayStateLevelAB(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();

        ebTime = .3f;
        efTime = 1000;
        ebigTime = 1000;
        level = 2;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.THREE);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
