using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Tyn;

public class GameStateManager {

    public GameState gameState;
    private GameState menuState;
    public GameState playState;
    public GameState battleState;
    Game1 game;

    public enum State {
        MENU,
        PLAY,
        BATTLE,
        GAMEOVER
    }

    public GameStateManager(Game1 game) {
        this.game = game;
        menuState = new MenuState(game, this);
        playState = new PlayState(game, this);
        setState(State.MENU);
    }

    public void setState(State state){

        switch (state) {
            case State.MENU:
                gameState = menuState;
                break;
            case State.PLAY:
                gameState = playState;
                break;
            case State.BATTLE:
                gameState = battleState;
                break;
        }
    }
    public void update(GameTime gametime){
        gameState.update(gametime);
    }
    public void draw(){
        gameState.draw();
    }
}