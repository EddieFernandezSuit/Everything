package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelCE extends PlayState{

    public PlayStateLevelCE(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = 1000;
        efTime = 1000;
        ebigTime = .1f;
        upTime = 5;
        level = 15;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.SIXTEEN);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
