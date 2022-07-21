package com.eddie.sshooter.GameStates;


import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Managers.GameStateManager;

public class PlayStateLevelD extends PlayState{

    public PlayStateLevelD(GameStateManager gsm){
        super(gsm);
    }

    public void init() {

        super.init();


        ebTime = .25f;
        efTime = .25f;
        ebigTime = .25f;
        upTime = 5;
        level = 16;
    }
    public void update(float dt){
        super.update(dt);

        if(lvlTimer > lvlTime) {
            gsm.setState(GameStateManager.MENU);
            lvlTimer = 0;
            difTimer = 1;
        }
    }
}
