package com.eddie.sshooter.Entities.Enemies;

import com.eddie.sshooter.Entities.Entity;
import com.eddie.sshooter.GameStates.PlayState;


public class Enemy extends Entity {



    public void update(float dt){

        x += -speed * PlayState.getDifTimer() * dt;

        if ( x < -1 * width * scaledSize ){
            remove = true;
        }
    }

}
