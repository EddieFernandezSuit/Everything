package com.eddie.sshooter.GameStates;

import com.eddie.sshooter.Entities.Bullet;
import com.eddie.sshooter.Entities.Player;
import com.eddie.sshooter.Main.MainGame;
import com.eddie.sshooter.Managers.GameStateManager;

import java.util.ArrayList;

public abstract class GameState {

    public Player player;
    protected GameStateManager gsm;
    protected GameState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }
    public abstract void init();
    public abstract void update(float dt);
    public abstract void draw();
    public abstract void handleInput();
    public abstract void dispose();
}
