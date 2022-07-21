package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelBD extends PlayState{

    public PlayStateLevelBD(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = .5f;
        efTime = .25f;
        ebigTime = 1000;
        level = 9;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.TEN);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
