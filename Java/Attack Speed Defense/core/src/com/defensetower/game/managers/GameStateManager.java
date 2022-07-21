package com.defensetower.game.managers;

import com.defensetower.game.gamestates.GameOverState;
import com.defensetower.game.gamestates.GameState;
import com.defensetower.game.gamestates.MenuState;
import com.defensetower.game.gamestates.PlayState;

import static com.defensetower.game.managers.GameStateManager.State.GAMEOVER;

public class GameStateManager {
    private GameState gameState;

    public enum State {
        MENU,
        PLAY,
        HIGHSCORE,
        GAMEOVER
    }

    public GameStateManager(){
        setState(State.MENU);
    }

    public void setState(State state){
        if (gameState != null) gameState.dispose();

        switch (state) {
            case MENU:
                gameState = new MenuState(this);
                break;
            case PLAY:
                gameState = new PlayState(this);
                break;
            case HIGHSCORE:

                break;
            case GAMEOVER:
                gameState = new GameOverState(this);
                break;
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
