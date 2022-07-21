package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelCD extends PlayState{

    public PlayStateLevelCD(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = .3f;
        efTime = 1000;
        ebigTime = .2f;
        level = 14;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.FIFTEEN);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
