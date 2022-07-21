package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelBE extends PlayState{

    public PlayStateLevelBE(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = 1000;
        efTime = .2f;
        ebigTime = 1000;
        level = 10;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.ELEVEN);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
