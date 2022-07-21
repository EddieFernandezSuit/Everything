package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelBA extends PlayState{

    public PlayStateLevelBA(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = 1000;
        efTime = .5f;
        ebigTime = 1000;
        level = 6;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.SEVEN);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
