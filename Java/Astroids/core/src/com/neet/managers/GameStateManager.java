package com.neet.managers;

import com.neet.gamestates.*;

public class GameStateManager {
    //current game state
    private GameState gameState;

    public static final int MENU = 0;
    public static final int PLAY = 893746;
    public static final int HIGHSCORE = 234;
    public static final int GAMEOVER = 1234;

    public GameStateManager(){
        setState(MENU);
    }

    public void setState(int state){

        if (gameState != null) gameState.dispose();
        if(state == MENU){
            gameState = new MenuState(this);
        }
        if(state == PLAY){
            gameState = new PlayState(this);
        }
        if(state == HIGHSCORE){
            gameState = new HighScoreState(this);
        }
        if(state == GAMEOVER){
            gameState = new GameOverState(this);
        }
    }
    public void update(float dt){
        gameState.update(dt);
    }
    public void draw(){
        gameState.draw();
    }

}
