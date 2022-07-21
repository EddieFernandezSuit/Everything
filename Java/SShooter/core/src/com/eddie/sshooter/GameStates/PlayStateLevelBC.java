package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelBC extends PlayState{

    public PlayStateLevelBC(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = .3f;
        efTime = .3f;
        ebigTime = 1000;
        level = 8;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.NINE);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
