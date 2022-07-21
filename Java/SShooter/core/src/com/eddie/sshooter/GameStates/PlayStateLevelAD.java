package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelAD extends PlayState{

    public PlayStateLevelAD(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();

        ebTime = .2f;
        efTime = 1000;
        ebigTime = 1000;
        level = 4;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.FIVE);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
