package com.mygdx.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.DOKN;

import javax.print.DocFlavor;


public class MenuState extends GameState {

    private SpriteBatch sb;
    private GlyphLayout layout;

    private final String title = "DOKN";
    private BitmapFont titleFont;
    private BitmapFont font;

    private int currentItem;
    private String[] menuItems;

    private final float TITLE_HEIGHT = 300;
    private final float STRING_SPACE = 40;

    public MenuState(GameStateManager gsm){
        super(gsm);
    }

    public void init(){

        sb = new SpriteBatch();
        layout = new GlyphLayout();

        font = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        titleFont = new BitmapFont(Gdx.files.internal("fonts/font.fnt"));
        menuItems = new String[]{
                "Play",
                "Quit"
        };
    }

    public void update(float dt){
        handleInput();
    }

    public void draw(){

        sb.setColor(1, 1, 1, 1);
        sb.begin();
        layout.setText(titleFont, title);
        titleFont.setColor(Color.BLACK);
        titleFont.draw(sb, title, (DOKN.WIDTH - layout.width) / 2, TITLE_HEIGHT);

        for(int i = 0; i < menuItems.length; i++) {
            layout.setText(font, menuItems[i]);
            if(currentItem == i) font.setColor(Color.RED);
            else font.setColor(Color.BLACK);
            font.draw(
                    sb,
                    menuItems[i],
                    (DOKN.WIDTH - layout.width) / 2,
                    TITLE_HEIGHT - 100 - STRING_SPACE * i
            );
        }

        sb.end();



    }

    public void handleInput(){

        if(GameKeys.isPressed(GameKeys.UP)){
            if(currentItem > 0) currentItem--;
        }

        if(GameKeys.isPressed(GameKeys.DOWN))
            if(currentItem < menuItems.length - 1){
                currentItem++;
            }
        if(GameKeys.isPressed(GameKeys.ENTER) || GameKeys.isPressed(GameKeys.SPACE)){
            select();
            }
    }

    private void select(){
        if(currentItem == 0){
            gsm.setState(GameStateManager.State.PLAY);
        }

        else if(currentItem == 1){
            Gdx.app.exit();
        }

    }

    public void dispose(){
        sb.dispose();
        titleFont.dispose();
        font.dispose();
    }
}
