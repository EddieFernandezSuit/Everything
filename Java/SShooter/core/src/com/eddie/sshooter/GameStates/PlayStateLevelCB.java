package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelCB extends PlayState{

    public PlayStateLevelCB(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = .3f;
        efTime = 1000;
        ebigTime = .3f;
        level = 12;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.THIRTEEN);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
