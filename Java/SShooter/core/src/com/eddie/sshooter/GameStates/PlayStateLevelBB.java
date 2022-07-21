package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelBB extends PlayState{

    public PlayStateLevelBB(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = 1000;
        efTime = .3f;
        ebigTime = 1000;
        level = 7;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.EIGHT);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
