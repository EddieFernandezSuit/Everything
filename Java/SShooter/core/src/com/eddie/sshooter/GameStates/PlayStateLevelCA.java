package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelCA extends PlayState{

    public PlayStateLevelCA(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = 1000;
        efTime = 1000;
        ebigTime = .3f;
        level = 11;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.TWELVE);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
