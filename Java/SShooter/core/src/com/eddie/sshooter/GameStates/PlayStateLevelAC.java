package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelAC extends PlayState{

    public PlayStateLevelAC(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();

        ebTime = .25f;
        efTime = 1000;
        ebigTime = 1000;
        level = 3;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.FOUR);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
