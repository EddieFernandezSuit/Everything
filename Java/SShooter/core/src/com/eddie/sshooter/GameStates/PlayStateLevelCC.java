package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelCC extends PlayState{

    public PlayStateLevelCC(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = 1000;
        efTime = .25f;
        ebigTime = .25f;
        level = 13;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.FOURTEEN);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
