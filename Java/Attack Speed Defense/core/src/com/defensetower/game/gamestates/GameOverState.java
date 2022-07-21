package com.defensetower.game.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.defensetower.game.MainGame;
import com.defensetower.game.managers.GameKeys;
import com.defensetower.game.managers.GameStateManager;

import static com.defensetower.game.gamestates.PlayState.level;
import static com.defensetower.game.gamestates.PlayState.lives;
import static com.defensetower.game.gamestates.PlayState.score;

public class GameOverState extends GameState {

    private SpriteBatch sb;
    private GlyphLayout layout;
    private GlyphLayout layout1;
    private GlyphLayout layout2;
    private BitmapFont font;

    public GameOverState(GameStateManager gsm){
        super(gsm);
    }

    @Override
    public void init() {
        sb = new SpriteBatch();
        layout = new GlyphLayout();
        layout1 = new GlyphLayout();
        layout2 = new GlyphLayout();
        font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void draw() {
        sb.setColor(1, 1, 1, 1);
        sb.begin();
        layout.setText(font, "GAME OVER");
        layout1.setText(font, "press space");
        layout2.setText(font, "Round" + level);
        font.setColor(Color.BLACK);
        font.draw(sb, "GAME OVER", (MainGame.WIDTH - layout.width) / 2, 400);
        font.draw(sb, "[press space]", (MainGame.WIDTH - layout1.width) / 2, 200);
        font.draw(sb, "Round: " + level, (MainGame.WIDTH - layout2.width) / 2, 300);

        sb.end();
    }

    @Override
    public void handleInput() {

        if(GameKeys.isPressed(GameKeys.SPACE)){
            gsm.setState(GameStateManager.State.MENU);
            lives = 20;
            score = 15;
            level = 0;
        }

    }

    @Override
    public void dispose() {

    }

}
