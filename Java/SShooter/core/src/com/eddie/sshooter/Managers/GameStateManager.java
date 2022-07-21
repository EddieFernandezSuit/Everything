package com.eddie.sshooter.Managers;

import com.eddie.sshooter.GameStates.*;

public class GameStateManager {
    //current game state
    private GameState gameState;

    public static final int MENU = 0;
    public static final int PLAY = 893746;
    public static final int HIGHSCORE = 234;
    public static final int GAMEOVER = 1234;
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int ELEVEN = 11;
    public static final int TWELVE = 12;
    public static final int THIRTEEN = 13;
    public static final int FOURTEEN = 14;
    public static final int FIFTEEN = 15;
    public static final int SIXTEEN = 16;

    public GameStateManager(){setState(MENU);
    }

    public void setState(int state){

        if (gameState != null) gameState.dispose();

        if(state == MENU){
            gameState = new MenuState(this);
        }
        if(state == PLAY){
            gameState = new PlayState(this);
        }
        if(state == ONE){
            gameState = new PlayStateLevelAA(this);
        }
        if(state == TWO){
            gameState = new PlayStateLevelAB(this);
        }
        if(state == THREE){
            gameState = new PlayStateLevelAC(this);
        }
        if(state == FOUR){
            gameState = new PlayStateLevelAD(this);
        }
        if(state == FIVE){
            gameState = new PlayStateLevelAE(this);
        }
        if(state == SIX){
            gameState = new PlayStateLevelBA(this);
        }
        if(state == SEVEN){
            gameState = new PlayStateLevelBB(this);
        }
        if(state == EIGHT){
            gameState = new PlayStateLevelBC(this);
        }
        if(state == NINE){
            gameState = new PlayStateLevelBD(this);
        }
        if(state == TEN){
            gameState = new PlayStateLevelBE(this);
        }
        if(state == ELEVEN){
            gameState = new PlayStateLevelCA(this);
        }
        if(state == TWELVE){
            gameState = new PlayStateLevelCB(this);
        }
        if(state == THIRTEEN){
            gameState = new PlayStateLevelCC(this);
        }
        if(state == FOURTEEN){
            gameState = new PlayStateLevelCD(this);
        }
        if(state == FIFTEEN){
            gameState = new PlayStateLevelCE(this);
        }
        if(state == SIXTEEN){
            gameState = new PlayStateLevelD(this);
        }

        //if(state == HIGHSCORE){
        //    gameState = new HighScoreState(this);
        //}
        //if(state == GAMEOVER){
        //    gameState = new GameOverState(this);
        //}
    }
    public void update(float dt){
        gameState.update(dt);
    }
    public void draw(){
        gameState.draw();
    }

}